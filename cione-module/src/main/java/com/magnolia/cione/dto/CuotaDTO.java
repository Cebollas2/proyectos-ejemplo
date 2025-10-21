package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class CuotaDTO {

	private String idCuota;
	private String descripcion;
	private String fechaProxFactura;
	private String importe;
	private String fechaFinCuota;

	public String getIdCuota() {
		return idCuota;
	}

	public void setIdCuota(String idCuota) {
		this.idCuota = idCuota;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechaProxFactura() {
		if(!CioneUtils.isEmptyOrNull(fechaProxFactura)) {
			fechaProxFactura = CioneUtils.changeDateFormat(fechaProxFactura, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}		
		return fechaProxFactura;
	}

	public void setFechaProxFactura(String fechaProxFactura) {
		this.fechaProxFactura = fechaProxFactura;
	}

	public String getImporte() {
		return CioneUtils.decimalToView(importe);
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getFechaFinCuota() {
		return fechaFinCuota;
	}

	public void setFechaFinCuota(String fechaFinCuota) {
		this.fechaFinCuota = fechaFinCuota;
	}

}
