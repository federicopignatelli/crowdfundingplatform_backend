package federicopignatelli.crowdfundingplatform_backend.services;

import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.NotFoundException;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUPDATEDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUPDATEResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.repositories.CampaignRepository;
import federicopignatelli.crowdfundingplatform_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    public NewCampaignResponseDTO save(NewCampaignDTO body, UUID userid){
        Campaign newcampaign = new Campaign();
        newcampaign.setTitle(body.title());
        newcampaign.setSubtitle(body.subtitle());
        newcampaign.setCategory(body.category());
        newcampaign.setDescription(body.description());
        newcampaign.setStartDate(LocalDate.now());
        newcampaign.setCampaignCover(body.campaignCover());

        User found = userRepository.findById(userid).orElseThrow(() -> new NotFoundException("User not found with"));
        newcampaign.setUserId(found);

        campaignRepository.save(newcampaign);
        return new NewCampaignResponseDTO(newcampaign.getTitle(), newcampaign.getUserId());
    }

    public Page<Campaign> getCampaign(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.campaignRepository.findAll(pageable);
    }

    public Campaign findById(UUID id) {
        return campaignRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public NewCampaignUPDATEResponseDTO findByIdAndUpdate(UUID id, NewCampaignUPDATEDTO body) {
        Campaign found = this.findById(id);

        if(body.title() != null){
            found.setTitle(body.title());
        }

        if(body.subtitle() != null){
            found.setSubtitle(body.subtitle());
        }

        if(body.category() != null){
            found.setCategory(body.category());
        }

        if(body.description() != null){
            found.setDescription(body.description());
        }

        if(body.campaignCover() != null){
            found.setCampaignCover(body.campaignCover());
        }

        campaignRepository.save(found);
        return new NewCampaignUPDATEResponseDTO(found.getCampaignId());
    }

    public void findByIdAndDelete(UUID campaignId) {
        Campaign found = this.findById(campaignId);
        campaignRepository.delete(found);
    }

    public List<Campaign> getCampaignByUserId(UUID userId) {
        return campaignRepository.findByUserId(userId);
    }
}
