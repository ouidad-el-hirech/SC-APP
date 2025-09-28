package DTO;

public class TopRendezvous {

	    private Integer mois;
	    private long totalRDV;

	    public TopRendezvous(Integer mois, long totalRDV) {
	    	this.mois = mois;
	        this.totalRDV = totalRDV;
	    }

		public Integer getMois() {
			return mois;
		}

		public void setMois(Integer mois) {
			this.mois = mois;
		}

		public long getTotalRDV() {
			return totalRDV;
		}

		public void setTotalRDV(long totalRDV) {
			this.totalRDV = totalRDV;
		}

	 
	}


