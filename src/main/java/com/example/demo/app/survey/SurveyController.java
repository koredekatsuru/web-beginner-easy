package com.example.demo.app.survey;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Survey;
import com.example.demo.service.SurveyService;

@Controller
@RequestMapping("/survey")
public class SurveyController {
	private final String KEY_TITLE = "title";
	private final String KEY_COMPLETE = "complete";
	private final String KEY_SURVEY_LIST = "surveyList";
	private final String TITLE_SURVEY_FORM = "Survey Form";
	private final String TITLE_CONFIRM_PAGE = "Confirm Page"; 
	private final String TITLE_REGISTERD = "Registerd";
	private final String TITLE_SURVEY_INDEX = "Survey Index";

	private final String PATH_FORM = "/form";
	private final String PATH_SURVEY_FORM = "survey/form";
	private final String PATH_SURVEY_FORM_REDIRECT = "redirect:/survey/form";
	private final String PATH_CONFIRM = "/confirm";
	private final String PATH_SURVEY_CONFIRM = "survey/confirm";
	private final String PATH_COMPLETE = "/complete";
	
	@Autowired
	private SurveyService surveyService;
	
	@GetMapping
	public String index(Model model) {
		
		List<Survey> list = surveyService.getAll();
		model.addAttribute(KEY_SURVEY_LIST, list);
		model.addAttribute(KEY_TITLE, TITLE_SURVEY_INDEX);
		return "survey/index";
	}
	
	@GetMapping(PATH_FORM)
	public String form(SurveyForm surveyForm, Model model) {
		
		model.addAttribute(KEY_TITLE, TITLE_SURVEY_FORM);
		return PATH_SURVEY_FORM;
	}
	
	@PostMapping(PATH_FORM)
	public String formGoBack(SurveyForm surveyForm, Model model) {
		
		model.addAttribute(KEY_TITLE, TITLE_SURVEY_FORM);
		return PATH_SURVEY_FORM;
	}
	
	
	@PostMapping(PATH_CONFIRM)
	public String confirm(@Validated SurveyForm surveyForm, 
			BindingResult result,
			Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute(KEY_TITLE, TITLE_SURVEY_FORM);
			return PATH_SURVEY_FORM;
		}
		model.addAttribute(KEY_TITLE, TITLE_CONFIRM_PAGE);
		return PATH_SURVEY_CONFIRM;
	}
	
	@PostMapping(PATH_COMPLETE)
	public String complete(@Validated SurveyForm surveyForm, 
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			model.addAttribute(KEY_TITLE, TITLE_SURVEY_FORM);
			return PATH_SURVEY_FORM;
		}
		
		Survey survey = new Survey();
		survey.setAge(surveyForm.getAge());
		survey.setSatisfaction(surveyForm.getSatisfaction());
		survey.setComment(surveyForm.getComment());
		survey.setCreated(LocalDateTime.now());
		
		surveyService.save(survey);
		
		redirectAttributes.addFlashAttribute(KEY_COMPLETE, TITLE_REGISTERD);
		return PATH_SURVEY_FORM_REDIRECT;
	}
	
}
