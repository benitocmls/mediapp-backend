package com.mitocode.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Entity
@Table(name = "signos")
public class Signos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idSignos;
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime fecha;
	
	private String temperatura;
	private String pulso;
	private String ritmoRespiratorio;
	

	@ManyToOne
	@JoinColumn(name = "id_paciente", nullable = false)	 
	private Paciente paciente;
	
	public int getIdSignos() {
		return idSignos;
	}
	public void setIdSignos(int idSignos) {
		this.idSignos = idSignos;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getPulso() {
		return pulso;
	}
	public void setPulso(String pulso) {
		this.pulso = pulso;
	}
	public String getRitmoRespiratorio() {
		return ritmoRespiratorio;
	}
	public void setRitmoRespiratorio(String ritmoRespiratorio) {
		this.ritmoRespiratorio = ritmoRespiratorio;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
}
