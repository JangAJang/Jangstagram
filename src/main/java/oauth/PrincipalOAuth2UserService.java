package oauth;
import auth.PrincipalDetails;
import entity.User;
import excpetion.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import oauth.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("UserRequest : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("profile"));
        }
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId;
        String nickname = oAuth2UserInfo.getUsername();
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if(userEntity == null){ //아이디 없음. 회원가입 진행
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .nickname(nickname)
                    .providerId(providerId)
                    .provider(provider)
                    //.firstName(firstname)
                    //.lastName(lastname)
                    //.phone(phone)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }

}