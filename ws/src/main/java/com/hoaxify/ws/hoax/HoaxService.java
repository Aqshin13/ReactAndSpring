package com.hoaxify.ws.hoax;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.hoaxify.ws.file.FileAttachment;
import com.hoaxify.ws.file.FileAttachmentRepository;
import com.hoaxify.ws.hoax.vm.HoaxSubmitVM;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class HoaxService {

    HoaxRepository hoaxRepository;

    UserService userService;

    FileAttachmentRepository fileAttachmentRepository;


    public HoaxService(HoaxRepository hoaxRepository, UserService userService, FileAttachmentRepository fileAttachmentRepository) {
        super();
        this.hoaxRepository = hoaxRepository;
        this.userService = userService;
        this.fileAttachmentRepository = fileAttachmentRepository;


    }

//    public void save(Hoax hoax, User user) {
//        hoax.setTimestamp(new Date());
//        hoax.setUser(user);
//        hoaxRepository.save(hoax);
//    }

    public void save(HoaxSubmitVM hoaxSubmitVM, User user) {
        Hoax hoax = new Hoax();
        hoax.setContent(hoaxSubmitVM.getContent());
        hoax.setTimestamp(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
        Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(hoaxSubmitVM.getAttachmentId());
        if(optionalFileAttachment.isPresent()) {
            FileAttachment fileAttachment = optionalFileAttachment.get();
            fileAttachment.setHoax(hoax);
            fileAttachmentRepository.save(fileAttachment);//update edir
        }
    }

    public Page<Hoax> getHoaxes(Pageable page) {
        return hoaxRepository.findAll(page);
    }

    public Page<Hoax> getHoaxesOfUser(String username, Pageable page) {
        User inDB = userService.getByUsername(username);
        return hoaxRepository.findByUser(inDB, page);
    }

//    public Page<Hoax> getOldHoaxes(long id, Pageable page) {
//        return hoaxRepository.findByIdLessThan(id, page);
//    }

    public Page<Hoax> getOldHoaxes(long id, String username, Pageable page) {
        Specification<Hoax> specification = idLessThan(id);
        if (username != null) {
            User inDB = userService.getByUsername(username);
            specification = specification.and(userIs(inDB));
        }
        return hoaxRepository.findAll(specification, page);
    }

//    public Page<Hoax> getOldHoaxesOfUser(long id, String username, Pageable page) {
//        User inDB = userService.getByUsername(username);
//        return hoaxRepository.findByIdLessThanAndUser(id, inDB, page);
//    }

//    public long getNewHoaxesCount(long id) {
//        return hoaxRepository.countByIdGreaterThan(id);
//    }

    public long getNewHoaxesCount(long id, String username) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User inDB = userService.getByUsername(username);
            specification = specification.and(userIs(inDB));
        }
        return hoaxRepository.count(specification);
    }
//    public long getNewHoaxesCountOfUser(long id, String username) {
//        User inDB = userService.getByUsername(username);
//        return hoaxRepository.countByIdGreaterThanAndUser(id, inDB);
//    }

    public List<Hoax> getNewHoaxes(long id, String username, Sort sort) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User inDB = userService.getByUsername(username);
            specification = specification.and(userIs(inDB));
        }
        return hoaxRepository.findAll(specification, sort);
    }

//    public List<Hoax> getNewHoaxes(long id, Sort sort) {
//        return hoaxRepository.findByIdGreaterThan(id, sort);
//    }

//    public List<Hoax> getNewHoaxesOfUser(long id, String username, Sort sort) {
//        User inDB = userService.getByUsername(username);
//        return hoaxRepository.findByIdGreaterThanAndUser(id, inDB, sort);
//    }

    Specification<Hoax> idLessThan(long id) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThan(root.get("id"), id);
        };
    }

    Specification<Hoax> userIs(User user) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }

    Specification<Hoax> idGreaterThan(long id) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("id"), id);
        };
    }

}