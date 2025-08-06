package com.whatsong.domain.websocket.model;

import com.whatsong.domain.websocket.data.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
	private String nickname;
	private String message;
	private MessageType messageType;
}
