package rsp.service;

import rsp.enums.AchievementType;
import rsp.exception.NotFoundException;
import rsp.model.Achievement;
import rsp.model.User;
import rsp.repo.AchievementRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.AchievementService;

public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepo repo;

    public AchievementServiceImpl(AchievementRepo repo) {
        this.repo = repo;
    }

    @Override
    public Achievement create(AchievementType achievementType) {
        Achievement result = new Achievement();
        String name = "";
        String description = "";
        String imgSource = "";
        boolean changeFlag = false;

        switch(achievementType) {
            case CARDS_LEARNED_10: {
                name = "First 10 cards";
                description = "Learn 10 cards from a deck";
                imgSource = "";
                changeFlag = true;

                break;
            }

            case CARDS_LEARNED_25: {
                name = "25 cards memorized";
                description = "Learn 25 cards from a deck";
                imgSource = "";
                changeFlag = true;

                break;
            }

            case CARDS_LEARNED_50: {
                name = "50 cards done";
                description = "Learn 50 cards from a deck";
                imgSource = "";
                changeFlag = true;

                break;
            }

            case DECK_LEARNED: {
                name = "The first deck went!";
                description = "Learn all cards from 1 deck";
                imgSource = "";
                changeFlag = true;

                break;
            }

            case DECK_LEARNED_5: {
                name = "5 decks learned";
                description = "Learn all cards from 5 decks";
                imgSource = "";
                changeFlag = true;

                break;
            }

        }

        if(changeFlag) {
            result.setName(name);
            result.setDescription(description);
            result.setImgSource(imgSource);
            result.setOwner(SecurityUtils.getCurrentUser());

            repo.save(result);

            return result;
        } else {
            throw NotFoundException.create(Achievement.class.getName(), achievementType);
        }
    }

}
