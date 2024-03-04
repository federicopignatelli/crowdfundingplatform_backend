package federicopignatelli.crowdfundingplatform_backend.controllers;

import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.BadRequestException;
import federicopignatelli.crowdfundingplatform_backend.exceptions.NotFoundException;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.repositories.CampaignRepository;
import federicopignatelli.crowdfundingplatform_backend.services.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @Autowired
    CampaignService campaignService;

    @Autowired
    CampaignRepository campaignRepository;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewCampaignResponseDTO save(@RequestBody @Validated NewCampaignDTO body, BindingResult validation, @AuthenticationPrincipal User userId) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().toString());
        }
        campaignService.save(body, userId.getUserId());
        return new NewCampaignResponseDTO(body.title(), body.subtitle());
    }

    @GetMapping("/getcampaigns")
    public Page<Campaign> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "startDate") String sortBy){
        return campaignService.getCampaign(page, size, sortBy);
    }

    @PutMapping("edit/{campaignId}")
    @PreAuthorize("hasAuthority('USER')")
    public NewCampaignUpdateResponseDTO findByIdAndUpdate(@PathVariable UUID campaignId, @RequestBody @Validated NewCampaignUpdateDTO body, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().toString());
        }
        campaignService.findByIdAndUpdate(campaignId, body);
        return new NewCampaignUpdateResponseDTO(campaignId, body.title(), body.subtitle(), body.category(), body.description());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<Campaign> getCampaignByUserId(@RequestParam UUID userId) {
        return this.campaignService.getCampaignByUserId(userId);
    }

    @PostMapping("/uploadcover/{campaignId}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAvatar (@RequestParam("image") MultipartFile file, @PathVariable UUID campaignId ) throws IOException {
        Campaign found = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NotFoundException("campaign not found with id: " + campaignId));
        return campaignService.uploadCoverCampaign(file, found.getCampaignId());
    }

    @DeleteMapping("/{campaignId}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID campaignId) {
        campaignService.findByIdAndDelete(campaignId);
    }
}
