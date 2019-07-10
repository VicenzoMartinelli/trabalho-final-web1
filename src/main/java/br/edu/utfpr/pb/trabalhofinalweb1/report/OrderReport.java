package br.edu.utfpr.pb.trabalhofinalweb1.report;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Repository
public class OrderReport {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public JasperPrint generateReport(InputStream reportPath, Long orderId) throws SQLException, IOException, JRException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportPath);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ORDERID", orderId);
		parameters.put("SUBREPORT_DIR", this.getClass().getResource("/static/reportfiles/1_venda_sub.jasper").getPath().replace("%20", " "));

		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
		return print;
	}
	
}
