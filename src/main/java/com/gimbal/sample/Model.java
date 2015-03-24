package com.gimbal.sample;

import android.util.Log;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable {
	private int counter;

	Model() {
		counter = 0;
	}

	// Data methods
	public int getCounterValue() {
		return counter;
	}

	public void setCounterValue(int i) {
		counter = i;
		Log.d("DEMO", "Model: set counter to " + counter);
		setChanged();
		notifyObservers();
	}

	public void incrementCounter() {
		counter++;
		Log.d("DEMO", "Model: increment counter to " + counter);
		setChanged();
		notifyObservers();
	}

	// Observer methods
	
	// a helper to make it easier to initialize all observers
	public void initObservers() {
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void addObserver(Observer observer) {
		Log.d("DEMO", "Model: Observer added");
		super.addObserver(observer);
	}
	
	@Override
	public synchronized void deleteObserver(Observer observer) {
		Log.d("DEMO", "Model: Observer deleted");
		super.deleteObserver(observer);
	}

	@Override
	public synchronized void deleteObservers() {
		super.deleteObservers();
	}

	@Override
	public void notifyObservers() {
		Log.d("DEMO", "Model: Observers notified");
		super.notifyObservers();
	}

}