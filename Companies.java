package com.bpmigas.pma.dashboard.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bpmigas.pma.dashboard.CommonFunction;
import com.bpmigas.pma.dashboard.db.HibernateTools;
import com.bpmigas.pma.dashboard.secman.WebUser;

// Generated Dec 15, 2010 3:30:07 PM by Hibernate Tools 3.3.0.GA

/**
 * Companies generated by hbm2java
 */
/**
 * kelas ini menampung informasi dari tabel Companies
 * untuk informasi lebih lanjut,silakan melihat dokumentasi database
 */
public class Companies implements java.io.Serializable {
	public static class CompanyType {
		public static final Byte KKKS = 1;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4965859133724170644L;
	private String companyId;
	private String companyName;
	private Byte companyType = CompanyType.KKKS;
	private String wilayah;
	private String region;
	private String scmcf;
	private String sipmCode;
	private String shore;
	private List<Object[]> contacts;
	
	private String npwp;
	private String address;
	private String contract;
	private Date contractDate;
	private Date contractEndDate;
	private String operationArea;
	private String pimpinan;

	public Companies() {
	}

	public Companies(String companyId, String companyName, Byte companyType) {
		setCompanyId(companyId);
		this.companyName = companyName;
		this.companyType = companyType;
	}

	public Companies(String companyId, String companyName, Byte companyType,
			String wilayah, String region, String scmcf, String shore) {
		setCompanyId(companyId);
		this.companyName = companyName;
		this.companyType = companyType;
		this.wilayah = wilayah;
		this.region = region;
		this.scmcf = scmcf;
		this.shore = shore;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId.toUpperCase();	// selalu upper case!
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Byte getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(Byte companyType) {
		if (companyType == null)
			companyType = CompanyType.KKKS;
		this.companyType = companyType;
	}
	
	public String getCompanyTypeString() {
		return HibernateTools.queryAsMap("SELECT lineNum, description FROM ValueList WHERE listName='COMPTYPE' ORDER BY lineNum").get(getCompanyType() + "");
	}

	public String getWilayah() {
		return this.wilayah;
	}

	public void setWilayah(String wilayah) {
		this.wilayah = wilayah;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getScmcf() {
		return this.scmcf;
	}

	public void setScmcf(String scmcf) {
		this.scmcf = scmcf;
	}
	
	

	/**
	 * @return the sipmCode
	 */
	public String getSipmCode() {
		return sipmCode;
	}

	/**
	 * @param sipmCode the sipmCode to set
	 */
	public void setSipmCode(String sipmCode) {
		this.sipmCode = sipmCode;
	}
	
	public String getShore() {
		return shore;
	}

	public void setShore(String shore) {
		this.shore = shore;
	}

	@Override
	public String toString() {
		return "Companies [companyId=" + companyId + ", companyName="
				+ companyName + ", companyType=" + companyType + ", wilayah="
				+ wilayah + ", region=" + region + ", scmcf=" + scmcf + 
				", sipm=" + sipmCode + ", shore=" + shore + "]";
	}

	public static Map<String, String> getSearchList() {
		return HibernateTools.queryAsMap("select companyId, concat(companyName, ' [KKKS ', case when companyType in ('2','4') then 'Eksplorasi' else 'Eksploitasi' end, ']') from Companies c ORDER BY companyId");
	}
	
	public static Map<String, String> getSearchEmailList() {
		return HibernateTools.queryAsMap("select concat(companyName, ' [KKKS ', case when companyType in ('2','4') then 'Eksplorasi' else 'Eksploitasi' end, ']'), companyId from Companies c ORDER BY companyId");
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getContact() {
		if (contacts == null) {
			contacts =
			(List<Object[]>) HibernateTools.getCurrentSessionNoException()
			 .createCriteria(WebUser.class)
			 .add(Restrictions.eq("domain", getCompanyId()))
			 //.add(Restrictions.eq("role", "kkks"))
			 .setProjection(Projections.projectionList().add(Projections.property("email")).add(Projections.property("longname")))
			 .list();
		}
		return contacts;
	}

	public String getMainContactEmail() {
		String ret = StringUtils.join(
				new FilterIterator(
					CommonFunction.getOneColumnIterator(getContact().iterator(), 0),
					new Predicate() {
						@Override
						public boolean evaluate(Object arg0) {
							return arg0 != null && !"".equals(arg0);
						}
					}
				)
				, ", ");
		return ret;
	}

	public Object getMainContactName() {
		String ret = StringUtils.join(
				new FilterIterator(
						CommonFunction.getOneColumnIterator(getContact().iterator(), 1),
						new Predicate() {
							@Override
							public boolean evaluate(Object arg0) {
								return arg0 != null && !"".equals(arg0);
							}
						}
					), ", ");
		return ret;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getOperationArea() {
		return operationArea;
	}

	public void setOperationArea(String operationArea) {
		this.operationArea = operationArea;
	}
	
	public String getPimpinan() {
		return pimpinan;
	}
	public void setPimpinan(String pimpinan) {
		this.pimpinan = pimpinan;
	}
	
	public String getNpwp() {
		return npwp;
	}
	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}
}
