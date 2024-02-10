package federicopignatelli.crowdfundingplatform_backend.controllers;

import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.BadRequestException;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.services.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @Autowired
    CampaignService campaignService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewCampaignResponseDTO save(@RequestBody @Validated NewCampaignDTO body, BindingResult validation, @AuthenticationPrincipal User userId) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().toString());
        }
        campaignService.save(body, userId.getUserId());
        return new NewCampaignResponseDTO(body.title(), userId);
    }

    @GetMapping("")
    public Page<Campaign> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "nomeContatto") String sortBy){
        return campaignService.getCampaign(page, size, sortBy);
    }

    @PutMapping("/{campaignId}")
    public NewCampaignUpdateResponseDTO findByIdAndUpdate(@PathVariable UUID campaignId, @RequestBody @Validated NewCampaignUpdateDTO body, BindingResult validation) throws BadRequestException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().toString());
        }
        campaignService.findByIdAndUpdate(campaignId, body);
        return new NewCampaignUpdateResponseDTO(campaignId);
    }

    @GetMapping("/{userId}")
    public List<Campaign> getCampaignByUserId(@RequestParam UUID userId) {
        return this.campaignService.getCampaignByUserId(userId);
    }
}
