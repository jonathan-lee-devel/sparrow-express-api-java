package io.jonathanlee.sparrowexpressapi.controller.organization;

import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizations/invitations")
public class OrganizationInvitationController {

  private final OrganizationInvitationService organizationInvitationService;

}
