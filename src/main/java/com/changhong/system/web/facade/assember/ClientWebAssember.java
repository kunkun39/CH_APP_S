package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.Client;
import com.changhong.system.web.facade.dto.ClientDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class ClientWebAssember {

    public static Client toClientDomain(ClientDTO clientDTO) {
        Client client = null;
        if(clientDTO == null) return null;

        if (clientDTO.getId() > 0) {
            client = (Client) EntityLoadHolder.getUserDao().findById(clientDTO.getId(), Client.class);
            client.setMacFrom(clientDTO.getMacFrom());
            client.setMacTo(clientDTO.getMacTo());
            client.setNote(clientDTO.getNote());
        } else {
            client = new Client(clientDTO.getMacFrom(), clientDTO.getMacTo(), clientDTO.getNote());
        }

        return client;
    }

    public static ClientDTO toClientDTO(Client client) {
        final int id = client.getId();
        final String macFrom = client.getMacFrom().toLowerCase();
        final String macTo = client.getMacTo().toLowerCase();
        final String note = client.getNote();
        final String createDate = CHDateUtils.getSimpleDateFormat(client.getTimestamp());

        ClientDTO dto =  new ClientDTO(id, macFrom, macTo, note, createDate);
        return dto;
    }

    public static List<ClientDTO> toClientDTOList(List<Client> clients) {
        List<ClientDTO> dtos = new ArrayList<ClientDTO>();
        if (clients != null) {
            for (Client client : clients) {
                dtos.add(toClientDTO(client));
            }
        }
        return dtos;
    }
}
