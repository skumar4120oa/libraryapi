package com.sathish.library.api.rest;

import com.sathish.library.domain.Member;
import com.sathish.library.exception.DataFormatException;
import com.sathish.library.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/library/members")
public class MemberController extends AbstractEventPublisher {

    @Autowired
    private MemberService memberService;

    //I should be able to add new members to the library
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createMember(@RequestBody Member member,
                                 HttpServletRequest request, HttpServletResponse response) {
        Member createdMember = this.memberService.createMember(member);
        response.setHeader("Member", request.getRequestURL().append("/").append(createdMember.getMemberid()).toString());
    }

    //I should be able to view a list of the current library members
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Page<Member> getAllMember(@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.memberService.getAllMembers(page, size);
    }

    //I should be able to search for a member by id
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Member getMember(@PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Member member = this.memberService.getMember(id);
        checkResourceFound(member);
        return member;
    }

    //I should be able to view and edit a member’s name and contact information
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMember(@PathVariable("id") Long id, @RequestBody Member member,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.memberService.getMember(id));
        if (id != member.getMemberid()) throw new DataFormatException("ID doesn't match!");
        this.memberService.updateMember(member);
    }

    
    //I should be able to delete the users
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.memberService.getMember(id));
        this.memberService.deleteMember(id);
    }
    
    //TODO
    /* I should be able to search for users that currently have an overdue book checked
	   out.
     * As a user, I should be able to see which books have been checked out by which
	   members*/
}
