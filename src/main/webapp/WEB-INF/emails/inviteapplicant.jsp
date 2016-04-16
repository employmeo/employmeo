<%
com.employmeo.objects.Person applicant = (com.employmeo.objects.Person) session.getAttribute("applicant");
String link = (String) session.getAttribute("link");
String fname = applicant.getPersonFname();
String prefix = "http://" + request.getServerName();
if (request.getServerPort() != 80) {
	prefix = prefix + ":" + request.getServerPort();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Invitation to Apply</title>
<style type="text/css">
/* Client-specific Styles */
#outlook a {
	padding: 0;
} /* Force Outlook to provide a "view in browser" menu link. */
body {
	width: 100% !important;
	-webkit-text-size-adjust: 100%;
	-ms-text-size-adjust: 100%;
	margin: 0;
	padding: 0;
}
/* Prevent Webkit and Windows Mobile platforms from changing default font sizes, while not breaking desktop design. */
.ExternalClass {
	width: 100%;
} /* Force Hotmail to display emails at full width */
.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font,
	.ExternalClass td, .ExternalClass div {
	line-height: 100%;
} /* Force Hotmail to display normal line spacing.  */
#backgroundTable {
	margin: 0;
	padding: 0;
	width: 100% !important;
	line-height: 100% !important;
}
img {
	outline: none;
	text-decoration: none;
	border: none;
	-ms-interpolation-mode: bicubic;
}
a img {
	border: none;
}
.image_fix {
	display: block;
}
p {
	margin: 0px 0px !important;
}
table td {
	border-collapse: collapse;
}
table {
	border-collapse: collapse;
	mso-table-lspace: 0pt;
	mso-table-rspace: 0pt;
}
a {
	color: #33b9ff;
	text-decoration: none;
	text-decoration: none !important;
}
/*STYLES*/
table[class=full] {
	width: 100%;
	clear: both;
}
/*IPAD STYLES*/
@media only screen and (max-width: 640px) {
	a[href^="tel"], a[href^="sms"] {
		text-decoration: none;
		color: #33b9ff; /* or whatever your want */
		pointer-events: none;
		cursor: default;
	}
	.mobile_link a[href^="tel"], .mobile_link a[href^="sms"] {
		text-decoration: default;
		color: #33b9ff !important;
		pointer-events: auto;
		cursor: default;
	}
	table[class=devicewidth] {
		width: 440px !important;
		text-align: center !important;
	}
	table[class=devicewidthinner] {
		width: 420px !important;
		text-align: center !important;
	}
	img[class=banner] {
		width: 440px !important;
		height: 220px !important;
	}
	img[class=colimg2] {
		width: 440px !important;
		height: 220px !important;
	}
}
/*IPHONE STYLES*/
@media only screen and (max-width: 480px) {
	a[href^="tel"], a[href^="sms"] {
		text-decoration: none;
		color: #ffffff; /* or whatever your want */
		pointer-events: none;
		cursor: default;
	}
	.mobile_link a[href^="tel"], .mobile_link a[href^="sms"] {
		text-decoration: default;
		color: #ffffff !important;
		pointer-events: auto;
		cursor: default;
	}
	table[class=devicewidth] {
		width: 280px !important;
		text-align: center !important;
	}
	table[class=devicewidthinner] {
		width: 260px !important;
		text-align: center !important;
	}
	img[class=banner] {
		width: 280px !important;
		height: 140px !important;
	}
	img[class=colimg2] {
		width: 280px !important;
		height: 140px !important;
	}
	td[class="padding-top15"] {
		padding-top: 15px !important;
	}
}
</style>
</head>
<body>
	<!-- Start of seperator -->
	<table width="100%" bgcolor="#eeeeee" cellpadding="0" cellspacing="0"
		border="0" st-sortable="seperator">
		<tbody>
			<tr>
				<td>
					<table width="600" align="center" cellspacing="0" cellpadding="0"
						border="0" class="devicewidth">
						<tbody>
							<tr>
								<td align="center" height="30"
									style="font-size: 1px; line-height: 1px;"
									st-content="separator"></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- End of seperator -->
	<!-- Start of header -->
	<table width="100%" bgcolor="#eeeeee" cellpadding="0" cellspacing="0"
		border="0" st-sortable="header">
		<tbody>
			<tr>
				<td>
					<table width="600" cellpadding="0" cellspacing="0" border="0"
						align="center" class="devicewidth">
						<tbody>
							<tr>
								<td width="100%">
									<table bgcolor="#aaaaaa" width="600" cellpadding="0"
										cellspacing="0" border="0" align="center" class="devicewidth">
										<tbody>
											<!-- Spacing -->
											<tr>
												<td height="5"
													style="font-size: 1px; line-height: 1px; mso-line-height-rule: exactly;">
												</td>
											</tr>
											<!-- Spacing -->
											<tr>
												<td>
													<!-- start of header -->
													<table width="180" border="0" align="right" valign="middle"
														cellpadding="0" cellspacing="0" class="devicewidth">
														<tbody>
															<tr>
																<td align="center" style="font-family: Helvetica, arial, sans-serif; font-size: 20px; color: #ffffff"
																	st-content="phone" height="60">Job Application
																</td>
															</tr>
														</tbody>
													</table><!-- end of header -->
												</td>
											</tr>
											<!-- Spacing -->
											<tr>
												<td height="5" style="font-size: 1px; line-height: 1px; mso-line-height-rule: exactly;">
												</td>
											</tr>
											<!-- Spacing -->
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- End of Header -->
	<!-- Start of main-banner -->
	<table width="100%" bgcolor="#eeeeee" cellpadding="0" cellspacing="0"
		border="0" st-sortable="banner">
		<tbody>
			<tr>
				<td>
					<table width="600" cellpadding="0" cellspacing="0" border="0"
						align="center" class="devicewidth">
						<tbody>
							<tr>
								<td width="100%">
									<table width="600" align="center" cellspacing="0"
										cellpadding="0" border="0" class="devicewidth">
										<tbody>
											<tr>
												<!-- start of image -->
												<td align="center"><a target="_blank" href="<%=link %>"> <img width="600" border="0" height="200" alt=""
														style="display: block; border: none; outline: none; text-decoration: none;" src="<%=prefix%>/images/chipotle-small.png" class="banner"	st-image="banner-image" /></a></td>
											</tr>
										</tbody>
									</table> <!-- end of image -->
								</td>
							</tr>
						</tbody>
					</table>
					<table width="600" cellpadding="0" cellspacing="0" border="0"
						align="center" class="devicewidth">
						<tbody>
							<tr>
								<td width="100%">
									<table width="600" height="200" align="center" cellspacing="0"
										cellpadding="20" bgcolor="#ffffff" class="devicewidth">
										<tbody>
											<tr>
												<!-- start of image -->
												<td align="left" cellpadding="20">
												 Dear <%=fname %>,<br/>
												 <br/>
												 <p>Congratulations, we are excited to invite you to complete a preliminary assessment for this position.
												 Our assessment can be completed on your mobile device or in a browser at this link: <a href="<%=link %>"><%=link%></a></p>
												</td>
											</tr>
											<tr>
												<td align="right" cellpadding="20">
												 <p><a href="<%=link %>"><img src="<%=prefix%>/images/apply_now_button.png" height="50" width="auto"></img></a></p>
												</td>
											</tr>
										</tbody>
									</table> <!-- end of image -->
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- End of main-banner -->
	<!-- Start of seperator -->
	<table width="100%" bgcolor="#eeeeee" cellpadding="0" cellspacing="0"
		border="0" st-sortable="seperator">
		<tbody>
			<tr>
				<td>
					<table width="600" align="center" cellspacing="0" cellpadding="0"
						border="0" class="devicewidth">
						<tbody>
							<tr>
								<td align="center" height="30"
									style="font-size: 1px; line-height: 1px;"
									st-content="separator"></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- End of seperator -->
	<!-- footer -->
	<table width="100%" bgcolor="#eeeeee" cellpadding="0" cellspacing="0"
		border="0" st-sortable="header">
		<tbody>
			<tr>
				<td>
					<table width="600" cellpadding="0" cellspacing="0" border="0"
						align="center" class="devicewidth">
						<tbody>
							<tr>
								<td width="100%">
									<table bgcolor="#aaaaaa" width="600" cellpadding="0"
										cellspacing="0" border="0" align="center" class="devicewidth">
										<tbody>
											<!-- Spacing -->
											<tr>
												<td height="5"
													style="font-size: 1px; line-height: 1px; mso-line-height-rule: exactly;">
												</td>
											</tr>
											<!-- Spacing -->
											<tr>
												<td>
													<!-- logo -->
													<table width="390" align="left" border="0" cellpadding="0"
														cellspacing="0" class="devicewidth">
														<tbody>
															<tr>
																<td width="390" height="60" align="center"><a
																	target="_blank" href="#"> <img src="<%=prefix%>/images/emp-logo-sm.png"
																		alt="" border="0" width="auto" height="60"
																		style="display: block; border: none; outline: none; text-decoration: none;"
																		st-image="logo" /></a></td>
															</tr>
														</tbody>
													</table> <!-- end of logo -->
												</td>
											</tr>
											<!-- Spacing -->
											<tr>
												<td height="5" style="font-size: 1px; line-height: 1px; mso-line-height-rule: exactly;">
												</td>
											</tr>
											<!-- Spacing -->
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- end of footer -->
	<!-- Start of Postfooter -->
	<table width="100%" bgcolor="#eeeeee" cellpadding="0" cellspacing="0"
		border="0" st-sortable="footer">
		<tbody>
			<tr>
				<td>
					<table width="600" cellpadding="0" cellspacing="0" border="0"
						align="center" class="devicewidth">
						<tbody>
							<tr>
								<td width="100%">
									<table width="600" cellpadding="0" cellspacing="0" border="0"
										align="center" class="devicewidth">
										<tbody>
											<!-- Spacing -->
											<tr>
												<td width="100%" height="21"></td>
											</tr>
											<!-- Spacing -->
											<tr>
												<td align="center" valign="middle" style="font-family: Helvetica, arial, sans-serif; font-size: 13px; color: #000000" st-content="preheader">
												This company is an equal opportunity employer
												</td>
											</tr>
											<!-- Spacing -->
											<tr>
												<td width="100%" height="20"></td>
											</tr>
											<!-- Spacing -->
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- End of postfooter -->
</body>
</html>