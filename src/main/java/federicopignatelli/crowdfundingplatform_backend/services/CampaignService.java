package federicopignatelli.crowdfundingplatform_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.NotFoundException;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignUpdateResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.repositories.CampaignRepository;
import federicopignatelli.crowdfundingplatform_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private Cloudinary cloudinary;

    public NewCampaignResponseDTO save(NewCampaignDTO body, UUID userid){
        Campaign newcampaign = new Campaign();
        newcampaign.setTitle(body.title());
        newcampaign.setSubtitle(body.subtitle());
        newcampaign.setCategory(body.category());
        newcampaign.setDescription(body.description());
        newcampaign.setFundsTarget(body.fundsTarget());
        newcampaign.setStartDate(LocalDate.now());
        newcampaign.setTotalFunds(0);


        User found = userRepository.findById(userid).orElseThrow(() -> new NotFoundException("User not found with"));
        newcampaign.setUserId(found);

        campaignRepository.save(newcampaign);
        return new NewCampaignResponseDTO(newcampaign.getTitle(), newcampaign.getSubtitle());
    }

    public Page<Campaign> getCampaign(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());
        return this.campaignRepository.findAll(pageable);
    }

    public Campaign findById(UUID id) {
        return campaignRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public NewCampaignUpdateResponseDTO findByIdAndUpdate(UUID id, NewCampaignUpdateDTO body) {
        Campaign found = this.findById(id);

        if(body.title() != null && !body.title().isEmpty()){
            found.setTitle(body.title());
        }

        if(body.subtitle() != null && !body.subtitle().isEmpty()){
            found.setSubtitle(body.subtitle());
        }

        if(body.category() != null && !body.category().isEmpty()){
            found.setCategory(body.category());
        }

        if(body.description() != null && !body.description().isEmpty()){
            found.setDescription(body.description());
        }

        campaignRepository.save(found);
        return new NewCampaignUpdateResponseDTO(found.getCampaignId(), found.getTitle(), found.getSubtitle(), found.getCategory(), found.getDescription());
    }

    public void findByIdAndDelete(UUID campaignId) {
        Campaign found = this.findById(campaignId);
        campaignRepository.delete(found);
    }

    public List<Campaign> getCampaignByUserId(UUID userId) {
        return campaignRepository.findByUserId(userId);
    }

    public String uploadCoverCampaign(MultipartFile file, UUID campaignId) throws IOException {
        Campaign found = campaignRepository.findById(campaignId).orElseThrow(() -> new NotFoundException(campaignId));
        String url = (String) cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");
        found.setCampaignCover(url);
        campaignRepository.save(found);
        return url;
    }
}
