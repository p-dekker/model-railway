alter table blocks add logical_direction varchar(255);

alter table command_stations add virtual bool not null default false;

update command_stations set virtual = true where id = 'virtual';
commit;

insert into command_stations(id, description, short_name, class_name, connect_via, serial_port, ip_address, network_port, ip_auto_conf, supports_decoder_control, supports_accessory_control, supports_feedback, supports_loco_synch, supports_accessory_synch, supports_loco_image_synch, supports_loco_function_synch, protocols, default_cs, enabled, last_used_serial, sup_conn_types, feedback_module_id, feedback_bus_count, feedback_bus_0_module_count, feedback_bus_1_module_count, feedback_bus_2_module_count, feedback_bus_3_module_count, virtual)
values('esu-ecos', 'ESU ECoS', 'ECoS', 'jcs.commandStation.esu.ecos.EsuEcosCommandStationImpl', 'NETWORK', null, null, 15471, true, true, true, true, true, true, true, true, 'DCC,MFX,MM', true, true, '1', 'NETWORK', '0', 0, 0, 0, 0, 0, true);
commit;

update jcs_version set db_version = '0.0.3', app_version = '0.0.3';
commit;