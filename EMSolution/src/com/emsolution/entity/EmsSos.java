/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "EMS_SOS")
public class EmsSos implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "TEXT_ID")
    private String textId;
    @Column(name = "PROCESS_DATE")
    private Date processDate;
    @Column(name = "DEALER_CODE")
    private String dealerCode;
    @Column(name = "CUSTOMER")
    private String customer;
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
    @Column(name = "EQUIP_NUMBER")
    private String equipNumber;
    @Column(name = "COMP_CODE")
    private String compCode;
    @Column(name = "COMP_DESC")
    private String compDesc;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "MANUFACTURER")
    private String manufacturer;
    @Column(name = "JOBSITE")
    private String jobsite;
    @Column(name = "SAMPLED_DATE")
    private Date sampledDate;
    @Column(name = "SHIP_TIME")
    private String shipTime;
    @Column(name = "FLUID_BRAND")
    private String fluidBrand;
    @Column(name = "FLUID_TYPE")
    private String fluidType;
    @Column(name = "FLUID_WEIGHT")
    private String fluidWeight;
    @Column(name = "FLUID_ADD")
    private String fluidAdd;
    @Column(name = "FLUID_ADD_UNITS")
    private String fluidAddUnits;
    @Column(name = "METER")
    private String meter;
    @Column(name = "METER_UNITS")
    private String meterUnits;
    @Column(name = "METER_ON_FLUID")
    private String meterOnFluid;
    @Column(name = "FLUID_CHANGED")
    private String fluidChanged;
    @Column(name = "FILTER_CHANGED")
    private String filterChanged;
    @Column(name = "KIDNEY_LOOP")
    private String kidneyLoop;
    @Column(name = "LABEL_NO")
    private String labelNo;
    @Column(name = "SHOP_JOB_NO")
    private String shopJobNo;
    @Column(name = "OVERALL_INTERP")
    private String overallInterp;
    @Column(name = "DEBRIS")
    private String debris;
    @Column(name = "W")
    private String w;
    @Column(name = "H2O")
    private String h2o;
    @Column(name = "A")
    private String a;
    @Column(name = "F")
    private String f;
    @Column(name = "ST")
    private String st;
    @Column(name = "OXI")
    private String oxi;
    @Column(name = "NIT")
    private String nit;
    @Column(name = "SUL")
    private String sul;
    @Column(name = "AL")
    private String al;
    @Column(name = "BA")
    private String ba;
    @Column(name = "B")
    private String b;
    @Column(name = "CD")
    private String cd;
    @Column(name = "CA")
    private String ca;
    @Column(name = "CR")
    private String cr;
    @Column(name = "CU")
    private String cu;
    @Column(name = "FE")
    private String fe;
    @Column(name = "PB")
    private String pb;
    @Column(name = "MG")
    private String mg;
    @Column(name = "MN")
    private String mn;
    @Column(name = "MO")
    private String mo;
    @Column(name = "NI")
    private String ni;
    @Column(name = "P")
    private String p;
    @Column(name = "K")
    private String k;
    @Column(name = "SI")
    private String si;
    @Column(name = "AG")
    private String ag;
    @Column(name = "NA")
    private String na;
    @Column(name = "S")
    private String s;
    @Column(name = "SN")
    private String sn;
    @Column(name = "TI")
    private String ti;
    @Column(name = "V")
    private String v;
    @Column(name = "ZN")
    private String zn;
    @Column(name = "ISO")
    private String iso;
    @Column(name = "U4")
    private String u4;
    @Column(name = "U6")
    private String u6;
    @Column(name = "U10")
    private String u10;
    @Column(name = "U14")
    private String u14;
    @Column(name = "U18")
    private String u18;
    @Column(name = "U21")
    private String u21;
    @Column(name = "U38")
    private String u38;
    @Column(name = "U50")
    private String u50;
    @Column(name = "PQI")
    private String pqi;
    @Column(name = "NAS")
    private String nas;
    @Column(name = "PC_RATING")
    private String pcRating;
    @Column(name = "PFC")
    private String pfc;
    @Column(name = "PGC")
    private String pgc;
    @Column(name = "TAN")
    private String tan;
    @Column(name = "TBN")
    private String tbn;
    @Column(name = "V100")
    private String v100;
    @Column(name = "V40")
    private String v40;
    @Column(name = "VI")
    private String vi;
    @Column(name = "INTERP_DATE_TIME")
    private Date interpDateTime;
    @Lob
    @Column(name = "INTERPRETATION_TEXT")
    private String interpretationText;
    @Column(name = "TIPO_ANALISE")
    private String tipoAnalise;
    @Column(name = "COOL_TYPE")
    private String coolType;
    @Column(name = "SAC")
    private String sac;
    @Column(name = "TT")
    private String tt;
    @Column(name = "CL")
    private String cl;
    @Column(name = "SO4")
    private String so4;
    @Column(name = "NO2")
    private String no2;
    @Column(name = "NO3")
    private String no3;
    @Column(name = "GLO")
    private String glo;
    @Column(name = "CON")
    private String con;
    @Column(name = "ODOR")
    private String odor;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "APPEARENCE")
    private String appearence;
    @Column(name = "OIL_APPEAR")
    private String oilAppear;
    @Column(name = "PPT_AMOUNT")
    private String pptAmount;
    @Column(name = "PPT_APPEAR")
    private String pptAppear;
    @Column(name = "PPT_COLOR")
    private String pptColor;
    @Column(name = "PPT_PROP")
    private String pptProp;
    @Column(name = "FOAM")
    private String foam;
    @Column(name = "MOLYBDATE")
    private String molybdate;
    @Column(name = "PH")
    private String ph;
    @Column(name = "TH")
    private String th;
    @Column(name = "MOO4")
    private String moo4;
    @Column(name = "SIO3")
    private String sio3;
    @Column(name = "BO3")
    private String bo3;
    @Column(name = "PO4")
    private String po4;
    @Column(name = "GL")
    private String gl;
    @Column(name = "FP")
    private String fp;
    @Column(name = "BP")
    private String bp;
    @Column(name = "TURBIDITY")
    private String turbidity;
    @Column(name = "IS_REJEITADO")
    private String isRejeitado;
    @Lob
    @Column(name = "OBS_REJEICAO")
    private String obsRejeicao;
    @JoinColumn(name = "ID_EMS_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private EmsSegmento idEmsSegmento;
    @Column(name = "DATA_IMPORTACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataImportacao;
    public EmsSos() {
    }

    public EmsSos(String textId) {
        this.textId = textId;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
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

    public Date getSampledDate() {
        return sampledDate;
    }

    public void setSampledDate(Date sampledDate) {
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

    public Date getInterpDateTime() {
        return interpDateTime;
    }

    public void setInterpDateTime(Date interpDateTime) {
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

    public String getCoolType() {
        return coolType;
    }

    public void setCoolType(String coolType) {
        this.coolType = coolType;
    }

    public String getSac() {
        return sac;
    }

    public void setSac(String sac) {
        this.sac = sac;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getSo4() {
        return so4;
    }

    public void setSo4(String so4) {
        this.so4 = so4;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo3() {
        return no3;
    }

    public void setNo3(String no3) {
        this.no3 = no3;
    }

    public String getGlo() {
        return glo;
    }

    public void setGlo(String glo) {
        this.glo = glo;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getOdor() {
        return odor;
    }

    public void setOdor(String odor) {
        this.odor = odor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAppearence() {
        return appearence;
    }

    public void setAppearence(String appearence) {
        this.appearence = appearence;
    }

    public String getOilAppear() {
        return oilAppear;
    }

    public void setOilAppear(String oilAppear) {
        this.oilAppear = oilAppear;
    }

    public String getPptAmount() {
        return pptAmount;
    }

    public void setPptAmount(String pptAmount) {
        this.pptAmount = pptAmount;
    }

    public String getPptColor() {
        return pptColor;
    }

    public void setPptColor(String pptColor) {
        this.pptColor = pptColor;
    }

    public String getPptProp() {
        return pptProp;
    }

    public void setPptProp(String pptProp) {
        this.pptProp = pptProp;
    }

    public String getFoam() {
        return foam;
    }

    public void setFoam(String foam) {
        this.foam = foam;
    }

    public String getMolybdate() {
        return molybdate;
    }

    public void setMolybdate(String molybdate) {
        this.molybdate = molybdate;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th;
    }

    public String getMoo4() {
        return moo4;
    }

    public void setMoo4(String moo4) {
        this.moo4 = moo4;
    }

    public String getSio3() {
        return sio3;
    }

    public void setSio3(String sio3) {
        this.sio3 = sio3;
    }

    public String getBo3() {
        return bo3;
    }

    public void setBo3(String bo3) {
        this.bo3 = bo3;
    }

    public String getPo4() {
        return po4;
    }

    public void setPo4(String po4) {
        this.po4 = po4;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(String turbidity) {
        this.turbidity = turbidity;
    }

    public EmsSegmento getIdEmsSegmento() {
        return idEmsSegmento;
    }

    public void setIdEmsSegmento(EmsSegmento idEmsSegmento) {
        this.idEmsSegmento = idEmsSegmento;
    }

	public String getPptAppear() {
		return pptAppear;
	}

	public void setPptAppear(String pptAppear) {
		this.pptAppear = pptAppear;
	}

	public Date getDataImportacao() {
		return dataImportacao;
	}

	public void setDataImportacao(Date dataImportacao) {
		this.dataImportacao = dataImportacao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (textId != null ? textId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsSos)) {
            return false;
        }
        EmsSos other = (EmsSos) object;
        if ((this.textId == null && other.textId != null) || (this.textId != null && !this.textId.equals(other.textId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsSos[ textId=" + textId + " ]";
    }
    
}
