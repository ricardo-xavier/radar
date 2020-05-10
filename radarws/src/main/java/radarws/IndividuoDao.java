package radarws;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndividuoDao {
	
	private static Map<String, Individuo> populacao = new HashMap<String, Individuo>();

	public static void atualiza(Individuo individuo) {
		Individuo aux = populacao.get(individuo.getId());
		if (aux == null) {
			populacao.put(individuo.getId(), individuo);
		} else {
			aux.setStatus(individuo.getStatus());
			aux.setLatitude(individuo.getLatitude());
			aux.setLongitude(individuo.getLongitude());
			aux.setAtualizacao(new Date().getTime());
		}
	}

	public static Individuo recupera(String id) {
		return populacao.get(id);
	}
	
	public static List<Individuo> getVizinhanca(Individuo individuo) {
		List<Individuo> vizinhanca = new ArrayList<Individuo>();
		for (String id : populacao.keySet()) {
			if (id.equals(individuo.getId())) {
				continue;
			}
			Individuo vizinho = populacao.get(id);
			int distancia = Distancia.haversineInM(individuo.getLatitude(), individuo.getLongitude(), 
					vizinho.getLatitude(), vizinho.getLongitude());
			vizinho.setDistancia(distancia);
			vizinhanca.add(vizinho);
		}
		return vizinhanca;
	}

	public static Map<String, Individuo> getPopulacao() {
		return populacao;
	}

	public static void setPopulacao(Map<String, Individuo> populacao) {
		IndividuoDao.populacao = populacao;
	}

}
