package com.byblosbank.ams.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byblosbank.ams.service.QueryWebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController {

	private final QueryWebClient queryWebClient;

	private static final String LOGRESPONSEFORMAT = "Creatio Response {}";

	@Secured("ROLE_USER")
	@GetMapping({ "/0/odata/GlbAppointTopCat", "/0/odata/GlbAppointTop", "/0/odata/Account",
			"/0/odata/GlbVwClientAppointment" })
	public ResponseEntity<String> sendGetRequest(HttpServletRequest request) {

		String relativeUri = getRelativeURI(request);
		log.info("Received GET Request for {}", relativeUri);
		String response = queryWebClient.performGetQuery(relativeUri);

		log.info(LOGRESPONSEFORMAT, response);
		return ResponseEntity.ok(response);

	}

	@Secured("ROLE_USER")
	@PostMapping(value = { "/0/rest/GlbAMSWebService/GetBranchCROsByAppointmentTopicByDate",
			"/0/rest/GlbAMSWebService/GetAvailableSlots", "/0/odata/GlbProspectAppointment",
			"/0/rest/GlbAMSWebService/CreateAppointment" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendPostRequest(@RequestBody String requestData, HttpServletRequest request) {
		String relativeUri = request.getRequestURI();

		log.info("Received POST Request for {}", relativeUri);

		String response = queryWebClient.performPostQuery(relativeUri, requestData, request.getMethod());

		log.info(LOGRESPONSEFORMAT, response);
		return ResponseEntity.ok(response);

	}

	@Secured("ROLE_USER")
	@PatchMapping(value = { "/0/odata/Activity({appointmentId})" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendPatchRequest(@RequestBody String requestData, HttpServletRequest request) {
		String relativeUri = request.getRequestURI();

		log.info("Received PATCH Request for {}", relativeUri);

		String response = queryWebClient.performPostQuery(relativeUri, requestData, request.getMethod());

		log.info(LOGRESPONSEFORMAT, response);
		return ResponseEntity.ok(response);

	}

	private String getRelativeURI(HttpServletRequest request) {
		return request.getRequestURI().concat("?").concat(request.getQueryString());
	}

}
