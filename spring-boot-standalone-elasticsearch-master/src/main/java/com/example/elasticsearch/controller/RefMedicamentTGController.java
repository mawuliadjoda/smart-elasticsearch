package com.example.elasticsearch.controller;

import static com.example.elasticsearch.util.Config.REF_MEDIC_2_TOGO;
import static com.example.elasticsearch.util.Config.REF_MEDIC_TOGO;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elasticsearch.model.RefMedicamentTG;
import com.example.elasticsearch.util.ExcelReader;

@RestController
@RequestMapping("/rest")
public class RefMedicamentTGController {

	private static String index = "RefMedicamentTGs".toLowerCase();
	private static String type =  "RefMedicamentTG".toLowerCase();

	public static final String META_NAME_FIELD = "nom";

	@Autowired
	Client client;

	@PostMapping("/refMedicamentTGs")
	public void saveAll() throws IOException {

		ExcelReader excelReader = new ExcelReader();
		Collection<RefMedicamentTG> refMedicamentTGs = excelReader.readRefMedicamentTG(REF_MEDIC_TOGO);
		Collection<RefMedicamentTG> refMedicamentTG2s = excelReader.readRefMedicamentTG(REF_MEDIC_2_TOGO);

		List<RefMedicamentTG> list = new ArrayList<>();
		list.addAll(refMedicamentTGs);
		list.addAll(refMedicamentTG2s);
		System.out.println("start ...");
		for (RefMedicamentTG refMedicamentTG : list) {
			refMedicamentTG.setType("TG");
			client.prepareIndex(index, type, refMedicamentTG.getCis())
					.setSource(jsonBuilder().startObject().field("cis", refMedicamentTG.getCis())
							.field("nom", refMedicamentTG.getNom())
							.field("dci", refMedicamentTG.getDci())
							.field("classeTherapeutique", refMedicamentTG.getClasseTherapeutique())
							.field("formeDosage", refMedicamentTG.getFormeDosage())
							.field("typeM", refMedicamentTG.getType()).endObject())
					.get();
		}
		System.out.println("end ...");

	}
	
}
