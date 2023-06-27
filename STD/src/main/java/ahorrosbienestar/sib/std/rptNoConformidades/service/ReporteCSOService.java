package ahorrosbienestar.sib.std.rptNoConformidades.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.rptNoConformidades.repository.IReporteCSORepository;

@Service
public class ReporteCSOService {

	private IReporteCSORepository iReporteCSORepository;
	
	@Autowired
	public ReporteCSOService(IReporteCSORepository iReporteCSORepository) {
		this.iReporteCSORepository = iReporteCSORepository;
	}
	
}
