/*
The MIT License (MIT)

Copyright (c) 2013 Jacob Kanipe-Illig

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package com.hyphenated.card.domain;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="game")
public class Game implements Serializable {

	private static final long serialVersionUID = -495064662454346171L;
	private long id;
	private int playersRemaining;
	private Player playerInBTN;
	private GameType gameType;
	private String name;
	private boolean isStarted;
	private Set<Player> players;
	private HandEntity currentHand;
	private GameStructure gameStructure;
	
	@Column(name="game_id")
	@Id
	@jakarta.persistence.TableGenerator(name="GameIds", table="poker_id_generator", pkColumnName="entity_name", valueColumnName="next_id", pkColumnValue="Game", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="GameIds")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="players_left")
	public int getPlayersRemaining() {
		return playersRemaining;
	}
	public void setPlayersRemaining(int playersRemaining) {
		this.playersRemaining = playersRemaining;
	}
	
	@OneToOne
	@JoinColumn(name="btn_player_id")
	public Player getPlayerInBTN(){
		return playerInBTN;
	}
	public void setPlayerInBTN(Player playerInBTN){
		this.playerInBTN = playerInBTN;
	}
	
	@Column(name="game_type")
	@Enumerated(EnumType.STRING)
	public GameType getGameType() {
		return gameType;
	}
	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}
	
	@OneToMany(mappedBy="game", fetch=FetchType.LAZY)
	public Set<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="is_started")
	public boolean isStarted() {
		return isStarted;
	}
	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="current_hand_id")
	public HandEntity getCurrentHand() {
		return currentHand;
	}
	public void setCurrentHand(HandEntity currentHand) {
		this.currentHand = currentHand;
	}
	
	@OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="game_structure_id")
	public GameStructure getGameStructure() {
		return gameStructure;
	}
	public void setGameStructure(GameStructure gameStructure) {
		this.gameStructure = gameStructure;
	}
}
