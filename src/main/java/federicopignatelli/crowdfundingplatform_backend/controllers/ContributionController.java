package federicopignatelli.crowdfundingplatform_backend.controllers;

import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import federicopignatelli.crowdfundingplatform_backend.entities.Contribution;
import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.BadRequestException;
import federicopignatelli.crowdfundingplatform_backend.payload.Contribution.NewContributionDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.Contribution.NewContributionResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignDTO;
import federicopignatelli.crowdfundingplatform_backend.repositories.ContributionRepository;
import federicopignatelli.crowdfundingplatform_backend.services.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contribution")
public class ContributionController {
    @Autowired
    ContributionService contributionService;

    @Autowired
    ContributionRepository contributionRepository;

    @PostMapping("/create/{campaignId}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewContributionResponseDTO save(@RequestBody @Validated NewContributionDTO body, BindingResult validation, @AuthenticationPrincipal User userId, @PathVariable UUID campaignId){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().toString());
        }
        contributionService.createContribution(body, userId.getUserId(), campaignId);
        return new NewContributionResponseDTO(body.amount());
    }

    @GetMapping("/getcontributions")
    public Page<Contribution> findAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "100") int size,
                                      @RequestParam(defaultValue = "emissionDate") String sortBy){
        return contributionService.getContributions(page, size, sortBy);
    }
}
