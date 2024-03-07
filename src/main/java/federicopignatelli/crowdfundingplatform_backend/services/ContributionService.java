package federicopignatelli.crowdfundingplatform_backend.services;

import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import federicopignatelli.crowdfundingplatform_backend.entities.Contribution;
import federicopignatelli.crowdfundingplatform_backend.entities.User;
import federicopignatelli.crowdfundingplatform_backend.exceptions.NotFoundException;
import federicopignatelli.crowdfundingplatform_backend.payload.Contribution.NewContributionDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.Contribution.NewContributionResponseDTO;
import federicopignatelli.crowdfundingplatform_backend.payload.campaign.NewCampaignDTO;
import federicopignatelli.crowdfundingplatform_backend.repositories.CampaignRepository;
import federicopignatelli.crowdfundingplatform_backend.repositories.ContributionRepository;
import federicopignatelli.crowdfundingplatform_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class ContributionService {
    @Autowired
    ContributionRepository contributionRepository;

    @Autowired
    CampaignService campaignService;

    @Autowired
    UsersService usersService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CampaignRepository campaignRepository;

    public NewContributionResponseDTO createContribution(NewContributionDTO body, UUID userId, UUID campaignId, LocalTime emissionTime) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Utente non trovato con questo id"));

        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() ->
                new NotFoundException("Campagna non trovata con questo id"));

        Contribution contribution = new Contribution();
        contribution.setUserId(user);
        contribution.setCampaign(campaign);
        contribution.setAmount(body.amount());
        contribution.setEmissionTime(emissionTime);

        campaign.setTotalFunds(campaign.getTotalFunds() + body.amount());

        contributionRepository.save(contribution);
        campaignRepository.save(campaign);

        return new NewContributionResponseDTO(contribution.getAmount());
    }

    public Page<Contribution> getContributions(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.contributionRepository.findAll(pageable);
    }
}
