package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.report.OrderReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;

@Controller
@RequestMapping("relatorio")
public class RelatorioController {

	@Autowired
	private OrderReport orderReport;

	@GetMapping("order/download")
	public void orderDownload(
			HttpServletResponse response,
			@RequestParam("orderId") Long orderId
	) throws SQLException, IOException, JRException {
		JasperPrint jasperPrint = orderReport
				.generateReport(
						new ClassPathResource("static/reportfiles/1_venda.jasper").getInputStream()
						, orderId);
		download(jasperPrint, response);
	}
	
	private void download(JasperPrint jasperPrint, HttpServletResponse response) throws IOException, JRException {
		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", 
				String.format("attachment;filename=\"Pedido.pdf\""));
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	}
}
