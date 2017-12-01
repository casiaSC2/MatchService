package match.controller;


import match.commons.BaseResponse;
import match.commons.ErrorJson;
import match.commons.SuccessJson;
import match.service.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjian
 */
@RestController
public class MatchController {
    private Logger logger = LoggerFactory.getLogger(MatchController.class);
    @RequestMapping(value = "/control/on_off_switch", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse matchOnOff(){
        try {
            boolean isOn = ScheduleTask.matchOn.get();
            ScheduleTask.matchOn.compareAndSet(isOn, !isOn);
            return new SuccessJson();
        }catch (Exception e){
            logger.error("match switch error", e);
            return new ErrorJson(1, "match switch error");
        }
    }

}
