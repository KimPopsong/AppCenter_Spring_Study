package inu.appcenter.finalterm.service;

import inu.appcenter.finalterm.domain.Member;
import inu.appcenter.finalterm.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberPk) throws UsernameNotFoundException {
        Member findMember = memberRepository.findWithRolesById(Long.parseLong(memberPk))
                .orElseThrow(() -> new UsernameNotFoundException("Not Exists"));

        List<SimpleGrantedAuthority> authorities = findMember.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        return new User(findMember.getEmail(), "", authorities);
    }
}
