package DTO;

public class PrestationStatDTO {

	    private String prestation;
	    private long total;

	    public PrestationStatDTO(String prestation, long total) {
	        this.prestation = prestation;
	        this.total = total;
	    }

	    public String getPrestation() {
	        return prestation;
	    }

	    public void setPrestation(String prestation) {
	        this.prestation = prestation;
	    }

	    public long getTotal() {
	        return total;
	    }

	    public void setTotal(long total) {
	        this.total = total;
	    }
	}


