package io.jonathanlee.sparrowexpressapi.controller.organization;

import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationMembershipRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizations/membership-requests")
public class OrganizationMembershipRequestController {

  private final OrganizationMembershipRequestService organizationMembershipRequestService;

}
