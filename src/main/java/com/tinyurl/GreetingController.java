package com.tinyurl;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GreetingController {

	@Autowired
	private LongurlRepository repo;

	@Autowired
	private MongoTemplate template;


	@GetMapping("greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@RequestMapping("input")
	public String ShowForm(){
		return "input";
	}

	@RequestMapping("input2")
	public String ShowForm2(){
		return "input2";
	}


	@RequestMapping(path="input", method = RequestMethod.POST)
	public String shortenurl(@RequestParam String longurl, Model model){
		Longurl lurl = new Longurl(longurl);

		//lurl=template.save(lurl);

		try {
			template.save(lurl);
			String skey = lurl.getId();
			model.addAttribute("surl", skey);
		}catch(Exception e){
			Query q = new Query();
			q.addCriteria(Criteria.where("lUrl").is(longurl));
			List<Longurl> lurl2 =  template.find(q, Longurl.class);
			model.addAttribute("surl", lurl2.get(0).getId());
		}


		return "input";
	}

	@RequestMapping(path="input2", method = RequestMethod.POST)
	public String longenurl(@RequestParam String shorturl, Model model){


		Optional<Longurl> lurl;
		lurl = repo.findById(shorturl);

		Longurl lurl2;;
		if(lurl.isPresent()){
			lurl2 = lurl.get();
			model.addAttribute("lurl",lurl2.getlUrl());
		}
		else{
			model.addAttribute("lurl","not found");
		}
		return "input2";

	}

	@GetMapping("top10")
	public String showTop10(Model model){
		//List<Longurl> lurl10= repo.findAll(Sort.by(Sort.Direction.DESC, "_id")).stream().limit(10).toList();
		String topten ;

		Query q = new Query();
		q.with(Sort.by(Sort.Direction.DESC,"_id"));
		q.limit(10);

		List<Longurl> lurl10;
		lurl10 = template.find(q,Longurl.class);


		StringBuilder sb=new StringBuilder();
		for(Longurl ix:lurl10){
			sb.append(ix.getlUrl());
			sb.append(" , ");
		}
		topten = sb.toString();
		
		model.addAttribute("topten",topten);
		return "top10";
	}

}
