package it.polito.tdp.extflightdelays.model;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.*;
import it.polito.tdp.extflightdelays.db.*;

import java.util.List;

import org.jgrapht.Graph;

public class Model {
	
	private Graph<Airport,DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	private ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();

	public Graph<Airport,DefaultWeightedEdge> analizzaAeroporti(int x) {
		List<Airport> aeroporti = dao.loadAllAirports();
		/* metodo lento
		for(Airport partenza: aeroporti) {
			for(Airport arrivo: aeroporti) {
				if(!partenza.equals(arrivo) && !grafo.containsEdge(partenza, arrivo)) {
					double somma = 0.0;
					int cnt = 0;
					List<Flight> voli = dao.voliPartenzaArrivo(partenza,arrivo);
					if(voli.size()!=0) {
						for(Flight volo: voli) {
							somma+=volo.getDistance();
							cnt++;
						}
					}
					if(somma/cnt > x) {
						if(!grafo.containsVertex(partenza))
							grafo.addVertex(partenza);
						if(!grafo.containsVertex(arrivo))
							grafo.addVertex(arrivo);
						grafo.setEdgeWeight(grafo.addEdge(partenza, arrivo), somma/cnt)  ;
					}
				}
			}
		}
		return grafo;
		*/
		for(Airport partenza: aeroporti) {
			for(Airport arrivo: aeroporti) {
				if(!partenza.equals(arrivo) && !grafo.containsEdge(partenza, arrivo)) {
					double distance = this.dao.voliAverageByPartenzaArrivo(partenza, arrivo);
					if(distance > x) {
						if(!grafo.containsVertex(partenza))
							grafo.addVertex(partenza);
						if(!grafo.containsVertex(arrivo))
							grafo.addVertex(arrivo);
						grafo.setEdgeWeight(grafo.addEdge(partenza, arrivo), distance);
					}
				}
			}
		}
		return grafo;
	}

}
