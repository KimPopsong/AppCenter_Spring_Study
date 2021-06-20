package inu.appcenter.seventh.service;

import inu.appcenter.seventh.model.MemberCreateRequest;
import inu.appcenter.seventh.model.MemberResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    MemberResponse create(MultipartFile image, MemberCreateRequest request);
}
