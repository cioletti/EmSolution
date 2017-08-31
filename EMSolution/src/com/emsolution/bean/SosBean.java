package com.emsolution.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Lob;

public class SosBean implements Serializable  {
	
    private String textId;
    private String processDate;
    private String dealerCode;
    private String customer;
    private String serialNumber;
    private String equipNumber;
    private String compCode;
    private String compDesc;
    private String model;
    private String manufacturer;
    private String jobsite;
    private String sampledDate;
    private String shipTime;
    private String fluidBrand;
    private String fluidType;
    private String fluidWeight;
    private String fluidAdd;
    private String fluidAddUnits;
    private String meter;
    private String meterUnits;
    private String meterOnFluid;
    private String fluidChanged;
    private String filterChanged;
    private String kidneyLoop;
    private String labelNo;
    private String shopJobNo;
    private String overallInterp;
    private String debris;
    private String w;
    private String h2o;
    private String a;
    private String f;
    private String st;
    private String oxi;
    private String nit;
    private String sul;
    private String al;
    private String ba;
    private String b;
    private String cd;
    private String ca;
    private String cr;
    private String cu;
    private String fe;
    private String pb;
    private String mg;
    private String mn;
    private String mo;
    private String ni;
    private String p;
    private String k;
    private String si;
    private String ag;
    private String na;
    private String s;
    private String sn;
    private String ti;
    private String v;
    private String zn;
    private String iso;
    private String u4;
    private String u6;
    private String u10;
    private String u14;
    private String u18;
    private String u21;
    private String u38;
    private String u50;
    private String pqi;
    private String nas;
    private String pcRating;
    private String pfc;
    private String pgc;
    private String tan;
    private String tbn;
    private String v100;
    private String v40;
    private String vi;
    private String interpDateTime;
    private String interpretationText;
    private String tipoAnalise;
    private String horimetro;
    private String horimetroOleo;
    private String fabricante;
    private String dataProcesso;
    
    
	public String getTextId() {
		return textId;
	}
	public void setTextId(String textId) {
		this.textId = textId;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getEquipNumber() {
		return equipNumber;
	}
	public void setEquipNumber(String equipNumber) {
		this.equipNumber = equipNumber;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public String getCompDesc() {
		return compDesc;
	}
	public void setCompDesc(String compDesc) {
		this.compDesc = compDesc;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getJobsite() {
		return jobsite;
	}
	public void setJobsite(String jobsite) {
		this.jobsite = jobsite;
	}
	public String getSampledDate() {
		return sampledDate;
	}
	public void setSampledDate(String sampledDate) {
		this.sampledDate = sampledDate;
	}
	public String getShipTime() {
		return shipTime;
	}
	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}
	public String getFluidBrand() {
		return fluidBrand;
	}
	public void setFluidBrand(String fluidBrand) {
		this.fluidBrand = fluidBrand;
	}
	public String getFluidType() {
		return fluidType;
	}
	public void setFluidType(String fluidType) {
		this.fluidType = fluidType;
	}
	public String getFluidWeight() {
		return fluidWeight;
	}
	public void setFluidWeight(String fluidWeight) {
		this.fluidWeight = fluidWeight;
	}
	public String getFluidAdd() {
		return fluidAdd;
	}
	public void setFluidAdd(String fluidAdd) {
		this.fluidAdd = fluidAdd;
	}
	public String getFluidAddUnits() {
		return fluidAddUnits;
	}
	public void setFluidAddUnits(String fluidAddUnits) {
		this.fluidAddUnits = fluidAddUnits;
	}
	public String getMeter() {
		return meter;
	}
	public void setMeter(String meter) {
		this.meter = meter;
	}
	public String getMeterUnits() {
		return meterUnits;
	}
	public void setMeterUnits(String meterUnits) {
		this.meterUnits = meterUnits;
	}
	public String getMeterOnFluid() {
		return meterOnFluid;
	}
	public void setMeterOnFluid(String meterOnFluid) {
		this.meterOnFluid = meterOnFluid;
	}
	public String getFluidChanged() {
		return fluidChanged;
	}
	public void setFluidChanged(String fluidChanged) {
		this.fluidChanged = fluidChanged;
	}
	public String getFilterChanged() {
		return filterChanged;
	}
	public void setFilterChanged(String filterChanged) {
		this.filterChanged = filterChanged;
	}
	public String getKidneyLoop() {
		return kidneyLoop;
	}
	public void setKidneyLoop(String kidneyLoop) {
		this.kidneyLoop = kidneyLoop;
	}
	public String getLabelNo() {
		return labelNo;
	}
	public void setLabelNo(String labelNo) {
		this.labelNo = labelNo;
	}
	public String getShopJobNo() {
		return shopJobNo;
	}
	public void setShopJobNo(String shopJobNo) {
		this.shopJobNo = shopJobNo;
	}
	public String getOverallInterp() {
		return overallInterp;
	}
	public void setOverallInterp(String overallInterp) {
		this.overallInterp = overallInterp;
	}
	public String getDebris() {
		return debris;
	}
	public void setDebris(String debris) {
		this.debris = debris;
	}
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w = w;
	}
	public String getH2o() {
		return h2o;
	}
	public void setH2o(String h2o) {
		this.h2o = h2o;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public String getOxi() {
		return oxi;
	}
	public void setOxi(String oxi) {
		this.oxi = oxi;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getSul() {
		return sul;
	}
	public void setSul(String sul) {
		this.sul = sul;
	}
	public String getAl() {
		return al;
	}
	public void setAl(String al) {
		this.al = al;
	}
	public String getBa() {
		return ba;
	}
	public void setBa(String ba) {
		this.ba = ba;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getCa() {
		return ca;
	}
	public void setCa(String ca) {
		this.ca = ca;
	}
	public String getCr() {
		return cr;
	}
	public void setCr(String cr) {
		this.cr = cr;
	}
	public String getCu() {
		return cu;
	}
	public void setCu(String cu) {
		this.cu = cu;
	}
	public String getFe() {
		return fe;
	}
	public void setFe(String fe) {
		this.fe = fe;
	}
	public String getPb() {
		return pb;
	}
	public void setPb(String pb) {
		this.pb = pb;
	}
	public String getMg() {
		return mg;
	}
	public void setMg(String mg) {
		this.mg = mg;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getMo() {
		return mo;
	}
	public void setMo(String mo) {
		this.mo = mo;
	}
	public String getNi() {
		return ni;
	}
	public void setNi(String ni) {
		this.ni = ni;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getSi() {
		return si;
	}
	public void setSi(String si) {
		this.si = si;
	}
	public String getAg() {
		return ag;
	}
	public void setAg(String ag) {
		this.ag = ag;
	}
	public String getNa() {
		return na;
	}
	public void setNa(String na) {
		this.na = na;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTi() {
		return ti;
	}
	public void setTi(String ti) {
		this.ti = ti;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getZn() {
		return zn;
	}
	public void setZn(String zn) {
		this.zn = zn;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public String getU4() {
		return u4;
	}
	public void setU4(String u4) {
		this.u4 = u4;
	}
	public String getU6() {
		return u6;
	}
	public void setU6(String u6) {
		this.u6 = u6;
	}
	public String getU10() {
		return u10;
	}
	public void setU10(String u10) {
		this.u10 = u10;
	}
	public String getU14() {
		return u14;
	}
	public void setU14(String u14) {
		this.u14 = u14;
	}
	public String getU18() {
		return u18;
	}
	public void setU18(String u18) {
		this.u18 = u18;
	}
	public String getU21() {
		return u21;
	}
	public void setU21(String u21) {
		this.u21 = u21;
	}
	public String getU38() {
		return u38;
	}
	public void setU38(String u38) {
		this.u38 = u38;
	}
	public String getU50() {
		return u50;
	}
	public void setU50(String u50) {
		this.u50 = u50;
	}
	public String getPqi() {
		return pqi;
	}
	public void setPqi(String pqi) {
		this.pqi = pqi;
	}
	public String getNas() {
		return nas;
	}
	public void setNas(String nas) {
		this.nas = nas;
	}
	public String getPcRating() {
		return pcRating;
	}
	public void setPcRating(String pcRating) {
		this.pcRating = pcRating;
	}
	public String getPfc() {
		return pfc;
	}
	public void setPfc(String pfc) {
		this.pfc = pfc;
	}
	public String getPgc() {
		return pgc;
	}
	public void setPgc(String pgc) {
		this.pgc = pgc;
	}
	public String getTan() {
		return tan;
	}
	public void setTan(String tan) {
		this.tan = tan;
	}
	public String getTbn() {
		return tbn;
	}
	public void setTbn(String tbn) {
		this.tbn = tbn;
	}
	public String getV100() {
		return v100;
	}
	public void setV100(String v100) {
		this.v100 = v100;
	}
	public String getV40() {
		return v40;
	}
	public void setV40(String v40) {
		this.v40 = v40;
	}
	public String getVi() {
		return vi;
	}
	public void setVi(String vi) {
		this.vi = vi;
	}
	public String getInterpDateTime() {
		return interpDateTime;
	}
	public void setInterpDateTime(String interpDateTime) {
		this.interpDateTime = interpDateTime;
	}
	public String getInterpretationText() {
		return interpretationText;
	}
	public void setInterpretationText(String interpretationText) {
		this.interpretationText = interpretationText;
	}
	public String getTipoAnalise() {
		return tipoAnalise;
	}
	public void setTipoAnalise(String tipoAnalise) {
		this.tipoAnalise = tipoAnalise;
	}
	public String getHorimetro() {
		return horimetro;
	}
	public void setHorimetro(String horimetro) {
		this.horimetro = horimetro;
	}
	public String getHorimetroOleo() {
		return horimetroOleo;
	}
	public void setHorimetroOleo(String horimetroOleo) {
		this.horimetroOleo = horimetroOleo;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getDataProcesso() {
		return dataProcesso;
	}
	public void setDataProcesso(String dataProcesso) {
		this.dataProcesso = dataProcesso;
	}

}
