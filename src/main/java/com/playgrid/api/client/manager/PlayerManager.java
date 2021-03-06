package com.playgrid.api.client.manager;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.playgrid.api.client.RestAPI;
import com.playgrid.api.entity.Player;
import com.playgrid.api.entity.PlayerAuthorization;
import com.playgrid.api.entity.PlayerRegistration;
import com.playgrid.api.entity.Players;

public class PlayerManager extends AbstractManager {

	public PlayerManager() {
		super();
	}

	@Override
	public Players all() {
		WebTarget target = restAPI.getEndpointTarget("player:list");
		Response response = target.request().get();
		return restAPI.translateResponse(response, Players.class);
	}

	public Player reload(Player player) {
		WebTarget webTarget = RestAPI.getInstance().createTarget(player.url);
		Response response = webTarget.request().get();
		return RestAPI.getInstance().translateResponse(response, Player.class);
	}

	public Player join(Player player) {
		return this.join(player, null);
	}

	public Player join(Player player, String stats) {
		WebTarget webTarget = player.getTarget().path("join/");

		Response response;
		if (stats == null) {
			response = webTarget.request().get();
		} else {
			response = webTarget.request().post(Entity.json(stats));
		}
		return RestAPI.getInstance().translateResponse(response, Player.class);
	}

	public Player quit(Player player) {
		return this.quit(player, null);
	}

	public Player quit(Player player, String stats) {
		WebTarget webTarget = player.getTarget().path("quit/");

		Response response;
		if (stats == null) {
			response = webTarget.request().get();
		} else {
			response = webTarget.request().post(Entity.json(stats));
		}
		return RestAPI.getInstance().translateResponse(response, Player.class);
	}
	
	public Player ban(Player player) {
		return action(player, null);
	}

	public Player suspend(Player player, String duration) {
		return action(player, duration);
	}

	private Player action(Player player, String duration) {
		WebTarget webTarget = player.getTarget();
		if (duration != null) {
			webTarget = webTarget.queryParam("action", "suspend")
								 .queryParam("duration", duration);
		} else {
			webTarget = webTarget.queryParam("action", "ban");
		}
		Response response = webTarget.request().put(Entity.json(player));
		return RestAPI.getInstance().translateResponse(response, Player.class);
	}
	
	public Player authorize(String name, String uid) {
		WebTarget webTarget = restAPI.getEndpointTarget("player:authorize");

		PlayerAuthorization auth = new PlayerAuthorization(name, uid);

		Response response = webTarget.request().put(Entity.json(auth));
		if (response.getStatus() == 405) {
			throw new NotAllowedException(response);
		}
		return RestAPI.getInstance().translateResponse(response, Player.class);
	}

	public PlayerRegistration register(String name, String uid, String email) {
		WebTarget webTarget = restAPI.getEndpointTarget("player:register");

		PlayerRegistration reg = new PlayerRegistration(name, uid, email);

		Response response = webTarget.request().put(Entity.json(reg));
		return RestAPI.getInstance().translateResponse(response, PlayerRegistration.class);
	}
}
