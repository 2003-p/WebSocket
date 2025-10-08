package com.objetdirect.gwt.umldrawer.client.analyzer;

import java.util.Date;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.objetdirect.gwt.umldrawer.client.viewer.ViewerBase;


public class GraphTestPanel extends SimplePanel{
	ViewerBase vb;
	DataTable dataTable;


	public GraphTestPanel(){
		// Create a pie chart visualization.
		LineChart lineChart = new LineChart(this.createTable(), createOptions());
		//VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
		lineChart.addSelectHandler(createSelectHandler(lineChart));
		this.add(lineChart);
	}

//	public GraphTestPanel(DataTable data){
//		// Create a pie chart visualization.
//		LineChart lineChart = new LineChart(data, createOptions());
//		//VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
//		lineChart.addSelectHandler(createSelectHandler(lineChart));
//		this.add(lineChart);
//	}

	public GraphTestPanel(DataTable data, ViewerBase vb){
		// Create a pie chart visualization.
		this.vb = vb;
		this.dataTable = data;
		LineChart lineChart = new LineChart(data, createOptions());
		//VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
		lineChart.addSelectHandler(createSelectHandler(lineChart));
		this.add(lineChart);
	}

	private  Options createOptions() {
		Options options = Options.create();
		options.setWidth(600);
		options.setHeight(360);
		options.setTitle("Simularity Graph");
		return options;
	}

	private SelectHandler createSelectHandler(final LineChart lineChart) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				String message = "";

				// May be multiple selections.
				JsArray<Selection> selections = lineChart.getSelections();
				Selection selection = selections.get(0);
				if (selection.isCell()) {
					// isCell() returns true if a cell has been selected.

					// getRow() returns the row number of the selected cell.
					int row = selection.getRow();
					// getColumn() returns the column number of the selected cell.
					int column = selection.getColumn();

					dataTable.removeRows(0, row);
					clear();
					LineChart lineChart = new LineChart(dataTable, createOptions() );
					//VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
					lineChart.addSelectHandler(createSelectHandler(lineChart));
					add(lineChart);
				}

//				for (int i = 0; i < selections.length(); i++) {
//					// add a new line for each selection
//					message += i == 0 ? "" : "\n";
//
//					Selection selection = selections.get(i);
//
//					if (selection.isCell()) {
//						// isCell() returns true if a cell has been selected.
//
//						// getRow() returns the row number of the selected cell.
//						int row = selection.getRow();
//						// getColumn() returns the column number of the selected cell.
//						int column = selection.getColumn();
//						message += "cell " + row + ":" + column + " selected";
//					} else if (selection.isRow()) {
//						// isRow() returns true if an entire row has been selected.
//
//						// getRow() returns the row number of the selected row.
//						int row = selection.getRow();
//						message += "row " + row + " selected";
//					} else {
//						// unreachable
//						message += "Line chart selections should be either row selections or cell selections.";
//						message += "  Other visualizations support column selections as well.";
//					}
//				}

			}
		};
	}

	private AbstractDataTable createTable() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.DATETIME, "Time");
		data.addColumn(ColumnType.NUMBER, "Simularity");
		data.addRows(3);
		data.setValue(0, 0, new Date(System.currentTimeMillis()));
		data.setValue(0, 1, 10);
		data.setValue(1, 0, new Date(System.currentTimeMillis() + 100000));
		data.setValue(1, 1, 30);
		data.setValue(2, 0, new Date(System.currentTimeMillis() + 200000));
		data.setValue(2, 1, 20);
		return data;
	}

}
