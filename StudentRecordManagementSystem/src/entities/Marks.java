package entities;

public class Marks {
	double maths;
	double computers;
	double astronomy;
	double genscience;
	double social;
	public double getMaths() {
		return maths;
	}
	public void setMaths(double maths) {
		this.maths = maths;
	}
	public double getComputers() {
		return computers;
	}
	public void setComputers(double computers) {
		this.computers = computers;
	}
	public double getAstronomy() {
		return astronomy;
	}
	public void setAstronomy(double astronomy) {
		this.astronomy = astronomy;
	}
	public double getGenscience() {
		return genscience;
	}
	public void setGenscience(double genscience) {
		this.genscience = genscience;
	}
	public double getSocial() {
		return social;
	}
	public void setSocial(double social) {
		this.social = social;
	}
	public Marks(double maths, double computers, double astronomy, double genscience, double social) {
		super();
		this.maths = maths;
		this.computers = computers;
		this.astronomy = astronomy;
		this.genscience = genscience;
		this.social = social;
	}
	public Marks() {
		super();
	}	
}
