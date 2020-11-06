package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Survey;
import com.example.demo.repository.SurveyDao;

@Service
public class SurveyServiceImpl implements SurveyService{

	@Autowired
	private SurveyDao dao;
	
	@Override
	public void save(Survey survey) {
		dao.insertSurvey(survey);
	}

	@Override
	public List<Survey> getAll() {
		if(dao.getAll().isEmpty()) {
			throw new InquiryNotFoundException("SQL error");
		}
		return dao.getAll();
	}

}