package com.sbz.agro.dto;

import java.util.ArrayList;
import java.util.List;

public class FieldItemsDto {
	
	private String name;
	private Boolean inErrorState;
	private List<ArrayDto> arrays = new ArrayList<>();
	
	public FieldItemsDto() {
		
	}
	
	public FieldItemsDto(String name, List<ArrayDto> arrays) {
		this.name = name;
		this.arrays = arrays;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public Boolean getInErrorState() {
		return inErrorState;
	}

	public void setInErrorState(Boolean inErrorState) {
		this.inErrorState = inErrorState;
	}

	public List<ArrayDto> getArrays() {
		return arrays;
	}

	public void setArrays(List<ArrayDto> arrays) {
		this.arrays = arrays;
	}

	public void addArray(ArrayDto array) {
		if(this.arrays == null) this.arrays = new ArrayList<>();
		this.arrays.add(array);
	}

	public static class ArrayDto {
		
		private Long id;
		private String pumpEUI;
		private String rainEUI;
		private Boolean arrayInErrorState;
		private Boolean pumpInErrorState;
		private List<SetDto> sets = new ArrayList<>();
		
		public ArrayDto() {
			
		}
		
		public ArrayDto(Long id, String pumpEUI, String rainEUI, List<SetDto> sets) {
			this.id = id;
			this.pumpEUI = pumpEUI;
			this.rainEUI = rainEUI;
			this.sets = sets;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPumpEUI() {
			return pumpEUI;
		}

		public void setPumpEUI(String pumpEUI) {
			this.pumpEUI = pumpEUI;
		}

		public String getRainEUI() {
			return rainEUI;
		}

		public void setRainEUI(String rainEUI) {
			this.rainEUI = rainEUI;
		}

		public Boolean getArrayInErrorState() {
			return arrayInErrorState;
		}

		public void setArrayInErrorState(Boolean arrayInErrorState) {
			this.arrayInErrorState = arrayInErrorState;
		}

		public Boolean getPumpInErrorState() {
			return pumpInErrorState;
		}

		public void setPumpInErrorState(Boolean pumpInErrorState) {
			this.pumpInErrorState = pumpInErrorState;
		}

		public List<SetDto> getSets() {
			return sets;
		}

		public void setSets(List<SetDto> sets) {
			this.sets = sets;
		}

		public void addSet(SetDto set) {
			if(this.sets == null) this.sets = new ArrayList<>();
			this.sets.add(set);
		}

		public static class SetDto {
			
			private String moistureEUI;
			private String valveEUI;
			private Integer position;
			private Boolean moistureInErrorState;
			private Boolean valveInErrorState;
			
			public SetDto() {
				
			}
			
			public SetDto(String moistureEUI, String valveEUI, Integer position) {
				this.moistureEUI = moistureEUI;
				this.valveEUI = valveEUI;
				this.position = position;
			}

			public String getMoistureEUI() {
				return moistureEUI;
			}

			public void setMoistureEUI(String moistureEUI) {
				this.moistureEUI = moistureEUI;
			}

			public String getValveEUI() {
				return valveEUI;
			}

			public void setValveEUI(String valveEUI) {
				this.valveEUI = valveEUI;
			}

			public Integer getPosition() {
				return position;
			}

			public void setPosition(Integer position) {
				this.position = position;
			}

			public Boolean getMoistureInErrorState() {
				return moistureInErrorState;
			}

			public void setMoistureInErrorState(Boolean moistureInErrorState) {
				this.moistureInErrorState = moistureInErrorState;
			}

			public Boolean getValveInErrorState() {
				return valveInErrorState;
			}

			public void setValveInErrorState(Boolean valveInErrorState) {
				this.valveInErrorState = valveInErrorState;
			}
			
		}
	}

}
