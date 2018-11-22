package org.communis.javawebintro.service.impls;

import org.communis.javawebintro.config.UserDetailsImpl;
import org.communis.javawebintro.dto.UserPasswordWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.dto.filters.UserFilterWrapper;
import org.communis.javawebintro.entity.User;
import org.communis.javawebintro.enums.ArticleStatus;
import org.communis.javawebintro.enums.UserStatus;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;
import org.communis.javawebintro.repository.UserRepository;
import org.communis.javawebintro.repository.specifications.UserSpecification;
import org.communis.javawebintro.service.UserService;
import org.communis.javawebintro.utils.CredentialsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service("userService")
@Transactional(rollbackFor = ServerException.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionRegistry sessionRegistry;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           @Qualifier("sessionRegistry") SessionRegistry sessionRegistry) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public Page<UserWrapper> getPage(UserFilterWrapper filter) throws ServerException {
        try {
            int pageNumber = (filter.getPage() < 1) ? 0 : filter.getPage();
            int pageSize = filter.getSize();

            Sort sortBy = new Sort(new Sort.Order(Sort.Direction.ASC, "login"));
            Pageable pageable = new PageRequest(pageNumber, pageSize, sortBy);

            return userRepository.findAll(UserSpecification.build(filter), pageable).map(UserWrapper::new);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_LIST_ERROR), ex);
        }
    }

    @Override
    public Long create(UserWrapper userWrapper) throws ServerException {
        try {
            User user = new User();
            userWrapper.fromWrapper(user);
            if (userRepository.findFirstByLogin(user.getLogin()).isPresent()) {
                throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_LOGIN_ALREADY_EXIST));
            }

            setPassword(user, new UserPasswordWrapper(userWrapper));

            user.setDateCreate(new Date());
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);

            return user.getId();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_ADD_ERROR), ex);
        }
    }

    @Override
    public UserWrapper getById(Long id) throws ServerException {
        return new UserWrapper(getUser(id));
    }

    @Override
    public Long update(UserWrapper userWrapper) throws ServerException {
        try {
            User user = getUser(userWrapper.getId());
            userWrapper.fromWrapper(user);
            userRepository.save(user);

            return user.getId();
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_UPDATE_ERROR), ex);
        }
    }

    @Override
    public void delete(Long id) throws ServerException {

        try {
            User user = getUser(id);

            if (user == getCurrentUser()) {
                throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_DELETE_SELF_ERROR));
            }

            Date date = new Date();
            user.setDateBlock(date);
            user.setStatus(UserStatus.DELETED);
            //TODO: скрыть все сообщения пользователя (если есть)
            user.getArticles().forEach(a -> {
                a.setStatus(ArticleStatus.BLOCKED);
                a.setDateClose(date);
            });

            userRepository.save(user);
            Optional<Object> userPrincipal = sessionRegistry.getAllPrincipals().stream()
                    .filter(p -> Objects.equals(((UserDetailsImpl) p).getUser().getId(), user.getId()))
                    .findFirst();
            userPrincipal.ifPresent(o -> sessionRegistry.getAllSessions(o, false)
                    .forEach(SessionInformation::expireNow));
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_DELETE_ERROR), ex);
        }
    }

    @Override
    public Long block(Long id) throws ServerException {
        try {
            User user = getUser(id);

            if (user == getCurrentUser()) {
                throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_BLOCK_SELF_ERROR));
            }

            user.setDateBlock(new Date());
            user.setStatus(UserStatus.BLOCKED);
            userRepository.save(user);
            Optional<Object> userPrincipal = sessionRegistry.getAllPrincipals().stream()
                    .filter(p -> Objects.equals(((UserDetailsImpl) p).getUser().getId(), user.getId()))
                    .findFirst();
            userPrincipal.ifPresent(o -> sessionRegistry.getAllSessions(o, false)
                    .forEach(SessionInformation::expireNow));

            return user.getId();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_BLOCK_ERROR), ex);
        }
    }

    @Override
    public Long unblock(Long id) throws ServerException {
        try {
            User user = getUser(id);
            user.setDateBlock(null);
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);

            return user.getId();
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_UNBLOCK_ERROR), ex);
        }
    }

    @Override
    public User getCurrentUser() throws ServerException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UserWrapper user = userDetails.getUser();
            return getUser(user.getId());
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_INFO_ERROR), ex);
        }
    }

    @Override
    public Long changePassword(UserPasswordWrapper passwordWrapper) throws ServerException {
        try {
            User user = getUser(passwordWrapper.getId());
            setPassword(user, passwordWrapper);
            userRepository.save(user);

            return user.getId();
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_ERROR), ex);
        }
    }

    private User getUser(Long id) throws ServerException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_INFO_ERROR)));
    }

    @Override
    public void updateLastOnlineDate(Long id) {
        User user = userRepository.findOne(id);
        user.setDateLastOnline(new Date());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findFirstByLogin(login)
                .map(UserWrapper::new)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCodeConstants.messages.get(ErrorCodeConstants.DATA_NOT_FOUND)));
    }


    private void setPassword(User user, UserPasswordWrapper passwordWrapper) throws ServerException {
        if (validatePassword(passwordWrapper)) {
            user.setPassword(bCryptPasswordEncoder.encode(passwordWrapper.getPassword()));
        }
    }

    private boolean validatePassword(UserPasswordWrapper passwordWrapper) throws ServerException {
        if (passwordWrapper.getPassword().length() < CredentialsUtil.PASSWORD_MIN_LENGTH) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_LENGTH_ERROR));
        }
        if (!Objects.equals(passwordWrapper.getPassword(), passwordWrapper.getConfirmPassword())) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_PASSWORD_COMPARE_ERROR));
        }
        return true;
    }
}
