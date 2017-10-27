package com.cloud.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class App_Info {
	private Long id;

	private String softwarename;

	private String apkname;

	private String supportrom;

	private String interfacelanguage;

	private BigDecimal softwaresize;

	private Date updatedate;

	private Long devid;

	private String appinfo;

	private Long status;

	private String statusname;

	private Date onsaledate;

	private Date offsaledate;

	private Long flatformid;

	private String flatformname;

	private Long downloads;

	private Long createdby;

	private Date creationdate;

	private Long modifyby;

	private Date modifydate;

	private Long categorylevel1;

	private String categorylevel1name;

	private Long categorylevel2;

	private String categorylevel2name;

	private Long categorylevel3;

	private String categorylevel3name;

	private String logopicpath;

	private String logolocpath;

	private Long versionid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSoftwarename() {
		return softwarename;
	}

	public void setSoftwarename(String softwarename) {
		this.softwarename = softwarename == null ? null : softwarename.trim();
	}

	public String getApkname() {
		return apkname;
	}

	public void setApkname(String apkname) {
		this.apkname = apkname == null ? null : apkname.trim();
	}

	public String getSupportrom() {
		return supportrom;
	}

	public void setSupportrom(String supportrom) {
		this.supportrom = supportrom == null ? null : supportrom.trim();
	}

	public String getInterfacelanguage() {
		return interfacelanguage;
	}

	public void setInterfacelanguage(String interfacelanguage) {
		this.interfacelanguage = interfacelanguage == null ? null : interfacelanguage.trim();
	}

	public BigDecimal getSoftwaresize() {
		return softwaresize;
	}

	public void setSoftwaresize(BigDecimal softwaresize) {
		this.softwaresize = softwaresize;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Long getDevid() {
		return devid;
	}

	public void setDevid(Long devid) {
		this.devid = devid;
	}

	public String getAppinfo() {
		return appinfo;
	}

	public void setAppinfo(String appinfo) {
		this.appinfo = appinfo == null ? null : appinfo.trim();
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getOnsaledate() {
		return onsaledate;
	}

	public void setOnsaledate(Date onsaledate) {
		this.onsaledate = onsaledate;
	}

	public Date getOffsaledate() {
		return offsaledate;
	}

	public void setOffsaledate(Date offsaledate) {
		this.offsaledate = offsaledate;
	}

	public Long getFlatformid() {
		return flatformid;
	}

	public void setFlatformid(Long flatformid) {
		this.flatformid = flatformid;
	}

	public Long getCategorylevel3() {
		return categorylevel3;
	}

	public void setCategorylevel3(Long categorylevel3) {
		this.categorylevel3 = categorylevel3;
	}

	public Long getDownloads() {
		return downloads;
	}

	public void setDownloads(Long downloads) {
		this.downloads = downloads;
	}

	public Long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Long createdby) {
		this.createdby = createdby;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Long getModifyby() {
		return modifyby;
	}

	public void setModifyby(Long modifyby) {
		this.modifyby = modifyby;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public Long getCategorylevel1() {
		return categorylevel1;
	}

	public void setCategorylevel1(Long categorylevel1) {
		this.categorylevel1 = categorylevel1;
	}

	public Long getCategorylevel2() {
		return categorylevel2;
	}

	public void setCategorylevel2(Long categorylevel2) {
		this.categorylevel2 = categorylevel2;
	}

	public String getLogopicpath() {
		return logopicpath;
	}

	public void setLogopicpath(String logopicpath) {
		this.logopicpath = logopicpath == null ? null : logopicpath.trim();
	}

	public String getLogolocpath() {
		return logolocpath;
	}

	public void setLogolocpath(String logolocpath) {
		this.logolocpath = logolocpath == null ? null : logolocpath.trim();
	}

	public Long getVersionid() {
		return versionid;
	}

	public void setVersionid(Long versionid) {
		this.versionid = versionid;
	}

	public String getFlatformname() {
		return flatformname;
	}

	public void setFlatformname(String flatformname) {
		this.flatformname = flatformname;
	}

	public String getCategorylevel3name() {
		return categorylevel3name;
	}

	public void setCategorylevel3name(String categorylevel3name) {
		this.categorylevel3name = categorylevel3name;
	}

	public String getCategorylevel1name() {
		return categorylevel1name;
	}

	public void setCategorylevel1name(String categorylevel1name) {
		this.categorylevel1name = categorylevel1name;
	}

	public String getCategorylevel2name() {
		return categorylevel2name;
	}

	public void setCategorylevel2name(String categorylevel2name) {
		this.categorylevel2name = categorylevel2name;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
}