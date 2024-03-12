///**
// * 
// */
//package com.ars.obcs.client;
//
////import com.ars.drismcm.rabbit.PerformanceTestMessage;
//import com.ars.drismcm.rabbit.PerformanceTestMessageAck;
//import com.ars.drismcm.station.StationManager;
//import com.ars.obcs.messages.BOAAbbreviatedDestNameAck;
//import com.ars.obcs.messages.BOAAbbreviatedDestNameReq;
//import com.ars.obcs.messages.BOAClusterDisplayConfigChangeReq;
//import com.ars.obcs.messages.BOAEntryExitGateInfoAck;
//import com.ars.obcs.messages.BOAEntryExitGateInfoReq;
//import com.ars.obcs.messages.BOAFreeTextChangeAck;
//import com.ars.obcs.messages.BOAKv8FreeTextMessageApprovalAck;
//import com.ars.obcs.messages.BOAKv8FreeTextMessageApprovalReq;
//import com.ars.obcs.messages.BOAKv8MessageConfigChangeAck;
//import com.ars.obcs.messages.BOAKv8MessageConfigChangeReq;
//import com.ars.obcs.messages.BOAOmloopAck;
//import com.ars.obcs.messages.BOAOmloopReq;
//import com.ars.obcs.messages.BOAPlatformClusterConfigChangeReq;
//import com.ars.obcs.messages.BOAPlatformConfigChangeAck;
//import com.ars.obcs.messages.BOARestartErrorResetAck;
//import com.ars.obcs.messages.BOARestartErrorResetReq;
//import com.ars.obcs.messages.BOARuleChangeAck;
//import com.ars.obcs.messages.BOASetGetLBSConfigAck;
//import com.ars.obcs.messages.BOASetGetLBSConfigReq;
//import com.ars.obcs.messages.BOASwitchOnOffDisplayAck;
//import com.ars.obcs.messages.BOATimingPointChangeAck;
//import com.ars.obcs.messages.BOATimingPointChangeReq;
//import com.ars.obcs.messages.BOATrolleyBusGWNChangeAck;
//import com.ars.obcs.messages.BOATrolleyBusGWNChangeReq;
//import com.ars.obcs.messages.BOAUpdateLEDConfigAck;
//import com.ars.obcs.messages.BOAUpdateLEDConfigReq;
//import com.ars.obcs.messages.CPHCallNotificationReq;
//import com.ars.obcs.messages.CPHCallPlanningMessageReq;
//import com.ars.obcs.messages.BOAFreeTextChangeReq;
//import com.ars.obcs.messages.BOAPlatformConfigChangeReq;
//import com.ars.obcs.messages.BOARuleChangeReq;
//import com.ars.obcs.messages.BOASwitchOnOffDisplayReq;
//import com.ars.obcs.messages.DcDisplayTextAck;
//import com.ars.obcs.messages.DcDisplayTextReq;
//import com.ars.obcs.messages.DcExecuteActionAck;
//import com.ars.obcs.messages.DcExecuteActionReq;
//import com.ars.obcs.messages.ISO1745LCVCUDetectionReq;
//import com.ars.obcs.messages.IVRSServiceDataAck;
//import com.ars.obcs.messages.IVRSServiceDataReq;
//import com.ars.obcs.messages.IVRSTripStatusReq;
//import com.ars.obcs.messages.IvrsDeviationReq;
//import com.ars.obcs.messages.KV4XmlMessageReq;
//import com.ars.obcs.messages.KVXmlDeviationMessageAck;
//import com.ars.obcs.messages.KVXmlDeviationMessageReq;
//import com.ars.obcs.messages.KVXmlTimeTableMessageAck;
//import com.ars.obcs.messages.KVXmlTimeTableMessageReq;
//import com.ars.obcs.messages.Kv5MessageAck;
//import com.ars.obcs.messages.Kv5MessageReq;
//import com.ars.obcs.messages.BOAUnUsedPlatformScheduleChangeAck;
//import com.ars.obcs.messages.BOAUnUsedPlatformScheduleChangeReq;
//import com.ars.obcs.messages.LCVCUDetectionAck;
//import com.ars.obcs.messages.LCVCUDetectionReq;
//import com.ars.obcs.messages.OBCSMessage;
//import com.ars.obcs.messages.BOAPlatformDisplayLinkAck;
//import com.ars.obcs.messages.BOAPlatformDisplayLinkReq;
//import com.ars.obcs.messages.OFPOmloopUpdateAck;
//import com.ars.obcs.messages.OFPOmloopUpdateReq;
//import com.ars.obcs.messages.UECBusAssignment_Req;
//import com.ars.obcs.messages.UECChangeModus_Req;
//import com.ars.obcs.messages.UECClearPlatformAllocation_Req;
//import com.ars.obcs.messages.UECClearPreferredPlatform_Req;
//import com.ars.obcs.messages.UECDefaultPlatform_Req;
//import com.ars.obcs.messages.UECKeepAlive_Req;
//import com.ars.obcs.messages.UECPlatformAllocation_Req;
//import com.ars.obcs.messages.UECPreferredPlatform_Req;
//import com.ars.obcs.messages.UECStatus_Ack;
//import com.ars.obcs.messages.UECStatus_Req;
//import com.ars.obcs.messages.UECTrolleyBusInfo_Ack;
//import com.ars.obcs.messages.UECTrolleyBusInfo_Req;
//import com.ars.obcs.messages.UECTrolleyInaccessiblePlaforms_Req;
//import com.ars.obcs.messages.UECTrolleyListClear_Req;
//import com.ars.obcs.messages.UECTrolleyList_Req;
//import com.ars.obcs.messages.VPSAffectedStops_Ex6;
//import com.ars.obcs.messages.VPSGenericMsgInterface;
//import com.ars.obcs.messages.VPSGenericMsg_Ack;
//import com.ars.obcs.messages.VPSGenericMsg_Req;
//import com.ars.obcs.messages.WDErrorStatusAck;
//import com.ars.obcs.messages.WDErrorStatusReq;
//import com.ars.obcs.messages.WDStatusAck;
//import com.ars.obcs.messages.WDStatusReq;
//
///**
// * @author manosh
// *
// */
//public class OBCSAssistImpl implements OBCSClientAssistInterface {
//	
//	private final StationManager stationManager;
//
//	public OBCSAssistImpl(StationManager station) {
//		this.stationManager = station;
//	}
//	
//	public boolean isRequestMessage(int messageID) {
//		switch (messageID) {
//		case DcDisplayTextReq.ID : 
//		case LCVCUDetectionReq.ID	 		:
//		case KVXmlTimeTableMessageReq.ID	:
//		case KVXmlDeviationMessageReq.ID 	:
//		case BOAFreeTextChangeReq.ID		 		:
//		case BOAOmloopReq.ID		 		:
//		case BOAUpdateLEDConfigReq.ID		:
//		case BOAPlatformConfigChangeReq.ID 	:
//		case BOARuleChangeReq.ID			:
//		case BOASwitchOnOffDisplayReq.ID	:
//		case BOAAbbreviatedDestNameReq.ID	:
//		case BOAPlatformDisplayLinkReq.ID	:
//		case BOASetGetLBSConfigReq.ID		:
//		case BOAEntryExitGateInfoReq.ID		:
//		case WDStatusReq.ID					:
//		case IVRSServiceDataReq.ID			:
//		case WDErrorStatusReq.ID 			:
//		case IVRSTripStatusReq.ID 			:
//		case IvrsDeviationReq.ID			:
//		case OFPOmloopUpdateReq.ID			:
//		case VPSGenericMsg_Req.ID			: //7026
//		case VPSAffectedStops_Ex6.ID		: //5033
//		case Kv5MessageReq.ID				:
//		case PerformanceTestMessage.ID			:
//		case KV4XmlMessageReq.ID			:
//		case BOATrolleyBusGWNChangeReq.ID :
//		case BOATimingPointChangeReq.ID		:
//		case BOAUnUsedPlatformScheduleChangeReq.ID :
//		case BOAKv8MessageConfigChangeReq.ID:
//		case BOARestartErrorResetReq.ID   :
//		case UECTrolleyList_Req.ID			://9000
//		case UECTrolleyListClear_Req.ID		://9001
//		case UECTrolleyBusInfo_Req.ID		://9002
//		case UECPlatformAllocation_Req.ID   ://9004
//		case UECClearPlatformAllocation_Req.ID://9005	
//		case UECPreferredPlatform_Req.ID    ://9006
//		case UECDefaultPlatform_Req.ID      ://9007
//		case UECClearPreferredPlatform_Req.ID://9008
//		case UECTrolleyInaccessiblePlaforms_Req.ID://9009
//		case UECStatus_Req.ID				://9010	
//		case UECKeepAlive_Req.ID			://9012
//		case UECBusAssignment_Req.ID		://9013
//		case UECChangeModus_Req.ID	        ://9014
//		case ISO1745LCVCUDetectionReq.ID	://9014	
//		case BOAKv8FreeTextMessageApprovalReq.ID :
//		case BOAClusterDisplayConfigChangeReq.ID :
//		case BOAPlatformClusterConfigChangeReq.ID :
//		case CPHCallPlanningMessageReq.ID: //9027
//		case CPHCallNotificationReq.ID:  //9028
//			return true;
//		default						 		: 
//			System.out.println("Some message recieved not able to process" + messageID );
//			return false;
//		}
//	}
//
//	public boolean isReqestNAcknowledement(OBCSMessage reqMessage,
//			OBCSMessage ackMessage) {
//		switch (reqMessage.getMessageID()) {
//		case DcDisplayTextReq.ID 			:
//			return ackMessage.getMessageID() == DcDisplayTextAck.ID 
//				&& ((DcDisplayTextReq) reqMessage).getSeqNo() == 
//					((DcDisplayTextAck) ackMessage).getSeqNo();
//		case WDErrorStatusReq.ID			:
//			return ackMessage.getMessageID() == WDErrorStatusAck.ID 
//				&& ((WDErrorStatusReq) reqMessage).getSeqNo() == 
//					((WDErrorStatusAck) ackMessage).getSeqNo();
//		case LCVCUDetectionReq.ID	 		:
//			return ackMessage.getMessageID() == LCVCUDetectionAck.ID 
//				&& ((LCVCUDetectionReq) reqMessage).getSeqNumber() == 
//					((LCVCUDetectionAck) ackMessage).getSeqNumber();
//		case KVXmlTimeTableMessageReq.ID	:
//			System.out.println("isReqestNAcknowledement : " + ackMessage.getMessageID() );
//			return ackMessage.getMessageID() == KVXmlTimeTableMessageAck.ID 
//				&& ((KVXmlTimeTableMessageReq) reqMessage).getSeqNo()==
//					((KVXmlTimeTableMessageAck) ackMessage).getSeqNo();
//		case KVXmlDeviationMessageReq.ID 	:
//			return ackMessage.getMessageID() == KVXmlDeviationMessageAck.ID 
//				&& ((KVXmlDeviationMessageReq) reqMessage).getSeqNo()==
//					((KVXmlDeviationMessageAck) ackMessage).getSeqNo();
//		case BOAFreeTextChangeReq.ID		 		:
//			return ackMessage.getMessageID() == BOAFreeTextChangeAck.ID 
//				&& ((BOAFreeTextChangeReq) reqMessage).getSeqNo()==
//					((BOAFreeTextChangeAck) ackMessage).getSeqNo();
//		case BOAOmloopReq.ID		 		:
//			return ackMessage.getMessageID() == BOAOmloopAck.ID 
//				&& ((BOAOmloopReq) reqMessage).getSeqNo()==
//					((BOAOmloopAck) ackMessage).getSeqNo();
//		case BOAUpdateLEDConfigReq.ID		:
//			return ackMessage.getMessageID() == BOAUpdateLEDConfigAck.ID 
//				&& ((BOAUpdateLEDConfigReq) reqMessage).getSeqNo()==
//					((BOAUpdateLEDConfigAck) ackMessage).getSeqNo();
//		case BOAPlatformConfigChangeReq.ID 	:
//			return ackMessage.getMessageID() == BOAPlatformConfigChangeAck.ID 
//				&& ((BOAPlatformConfigChangeReq) reqMessage).getSeqNo()==
//					((BOAPlatformConfigChangeAck) ackMessage).getSeqNo();
//		case BOARuleChangeReq.ID			:
//			return ackMessage.getMessageID() == BOARuleChangeAck.ID 
//				&& ((BOARuleChangeReq) reqMessage).getSeqNo()==
//					((BOARuleChangeAck) ackMessage).getSeqNo();
//		case BOASwitchOnOffDisplayReq.ID	:
//			return ackMessage.getMessageID() == BOASwitchOnOffDisplayAck.ID 
//				&& ((BOASwitchOnOffDisplayReq) reqMessage).getSeqNo()==
//					((BOASwitchOnOffDisplayAck) ackMessage).getSeqNo();
//		case BOAAbbreviatedDestNameReq.ID	:
//			return ackMessage.getMessageID() == BOAAbbreviatedDestNameAck.ID 
//				&& ((BOAAbbreviatedDestNameReq) reqMessage).getSeqNo()==
//					((BOAAbbreviatedDestNameAck) ackMessage).getSeqNo();
//		case BOAPlatformDisplayLinkReq.ID	:
//			return ackMessage.getMessageID() == BOAPlatformDisplayLinkAck.ID 
//				&& ((BOAPlatformDisplayLinkReq) reqMessage).getSeqNo()==
//					((BOAPlatformDisplayLinkAck) ackMessage).getSeqNo();
//		case BOASetGetLBSConfigReq.ID	:
//			return ackMessage.getMessageID() == BOASetGetLBSConfigAck.ID 
//			&& ((BOASetGetLBSConfigReq) reqMessage).getSeqNo() ==
//				((BOASetGetLBSConfigAck) ackMessage).getSeqNo();
//		case BOAEntryExitGateInfoReq.ID	:
//			return ackMessage.getMessageID() == BOAEntryExitGateInfoAck.ID
//			&& ((BOAEntryExitGateInfoReq) reqMessage).getSeqNo() ==
//				((BOAEntryExitGateInfoAck) ackMessage).getSeqNo();
//		case IVRSServiceDataReq.ID          :
//			return ackMessage.getMessageID() == IVRSServiceDataAck.ID
//			 &&((IVRSServiceDataReq)reqMessage).getSeqNo() == 
//				 ((IVRSServiceDataAck)ackMessage).getSeqNo();
//		case IVRSTripStatusReq.ID          :
//			return ackMessage.getMessageID() == IVRSServiceDataAck.ID
//			 &&((IVRSTripStatusReq)reqMessage).getSeqNo() == 
//				 ((IVRSServiceDataAck)ackMessage).getSeqNo();	
//		case OFPOmloopUpdateReq.ID          :
//			return ackMessage.getMessageID() == OFPOmloopUpdateAck.ID
//			&&((OFPOmloopUpdateReq)reqMessage).getSeqNo() ==
//				 ((OFPOmloopUpdateAck)ackMessage).getSeqNo();
//			//newly added for VPS generic message 7026
//		case VPSGenericMsg_Req.ID			:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((VPSGenericMsg_Req)reqMessage).getSeqNo() == 
//					((VPSGenericMsg_Ack)ackMessage).getSeqNo();
//			//
//		case VPSAffectedStops_Ex6.ID 	:
//			return ackMessage.getMessageID() == VPSAffectedStops_Ex6.ID 
//				&& ((VPSAffectedStops_Ex6) reqMessage).getSeqNo()==
//					((KVXmlDeviationMessageAck) ackMessage).getSeqNo();
//			
//		case Kv5MessageReq.ID :
//			return ackMessage.getMessageID() == Kv5MessageAck.ID
//			   && ((Kv5MessageReq) reqMessage).getSeqNo() == 
//			    ((Kv5MessageAck) ackMessage).getSeqNo();
//		
//		case BOATrolleyBusGWNChangeReq.ID :
//			return ackMessage.getMessageID() == BOATrolleyBusGWNChangeAck.ID
//				&&((BOATrolleyBusGWNChangeReq) reqMessage).getSeqNo() ==
//				((BOATrolleyBusGWNChangeAck) ackMessage).getSeqNo();
//			
//		case BOATimingPointChangeReq.ID :
//			return ackMessage.getMessageID() == BOATimingPointChangeReq.ID
//				&&((BOATimingPointChangeReq) reqMessage).getSeqNo() ==
//				((BOATimingPointChangeAck) ackMessage).getSeqNo();
//			
//		case BOAUnUsedPlatformScheduleChangeReq.ID :
//			return ackMessage.getMessageID() == BOAUnUsedPlatformScheduleChangeAck.ID
//				&&((BOAUnUsedPlatformScheduleChangeReq) reqMessage).getSeqNo() ==
//				((BOAUnUsedPlatformScheduleChangeAck) ackMessage).getSeqNo();
//			
//		case BOAKv8MessageConfigChangeReq.ID:
//			return ackMessage.getMessageID()== BOAKv8MessageConfigChangeAck.ID
//			    &&((BOATrolleyBusGWNChangeReq) reqMessage).getSeqNo() ==
//			    ((BOAKv8MessageConfigChangeAck) ackMessage).getSeqNo();
//		case BOARestartErrorResetReq.ID:
//			return ackMessage.getMessageID()== BOARestartErrorResetAck.ID
//				    &&((BOARestartErrorResetReq) reqMessage).getSeqNo() ==
//				    ((BOARestartErrorResetAck) ackMessage).getSeqNo();
//		//UEC	
//		case UECTrolleyList_Req.ID :
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECTrolleyList_Req) reqMessage).getSeqNo() == 
//					((VPSGenericMsg_Ack) ackMessage).getSeqNo();	
//		case UECTrolleyListClear_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECTrolleyListClear_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();	
//		case UECTrolleyBusInfo_Req.ID:
//			return ackMessage.getMessageID() == UECTrolleyBusInfo_Ack.ID 
//				&& ((UECTrolleyBusInfo_Req) reqMessage).getSeqNo() == 
//				((UECTrolleyBusInfo_Ack) ackMessage).getSeqNo();	
//		case UECPlatformAllocation_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//			&& ((UECPlatformAllocation_Req) reqMessage).getSeqNo() == 
//			((VPSGenericMsg_Ack) ackMessage).getSeqNo();
//		case UECClearPlatformAllocation_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECClearPlatformAllocation_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();	
//		case UECPreferredPlatform_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECPreferredPlatform_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();	
//		case UECDefaultPlatform_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECDefaultPlatform_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();		
//		case UECClearPreferredPlatform_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECClearPreferredPlatform_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();
//		case UECStatus_Req.ID:
//			return ackMessage.getMessageID() == UECStatus_Ack.ID 
//				&& ((UECStatus_Req) reqMessage).getSeqNo() == 
//				((UECStatus_Ack) ackMessage).getSeqNo();	
//		case UECKeepAlive_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECKeepAlive_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();
//		case UECBusAssignment_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECBusAssignment_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();
//		case UECChangeModus_Req.ID:
//			return ackMessage.getMessageID() == VPSGenericMsg_Ack.ID 
//				&& ((UECChangeModus_Req) reqMessage).getSeqNo() == 
//				((VPSGenericMsg_Ack) ackMessage).getSeqNo();		
//		case BOAKv8FreeTextMessageApprovalReq.ID:
//			return ackMessage.getMessageID()== BOAKv8FreeTextMessageApprovalAck.ID
//				    &&((BOAKv8FreeTextMessageApprovalReq) reqMessage).getSeqNo() ==
//				    ((BOAKv8FreeTextMessageApprovalAck) ackMessage).getSeqNo();
//		case ISO1745LCVCUDetectionReq.ID	 		:
//			return ackMessage.getMessageID() == ISO1745LCVCUDetectionReq.ID 
//				&& ((ISO1745LCVCUDetectionReq) reqMessage).getSeqNumber() == 
//					((ISO1745LCVCUDetectionReq) ackMessage).getSeqNumber();
//			
//		case UECTrolleyInaccessiblePlaforms_Req.ID:
//			return ackMessage.getMessageID() == UECTrolleyInaccessiblePlaforms_Req.ID
//			&&((UECTrolleyInaccessiblePlaforms_Req)reqMessage).getSeqNo() ==
//			((UECTrolleyInaccessiblePlaforms_Req)ackMessage).getSeqNo();
//	
//		
//		default 							:
//			System.out.println("Testing Message ID : " + reqMessage.getMessageID()  );
//			return false;
//		}
//	}
//
//	public OBCSMessage createOBCSMessage(short type) {
//		System.out.println(type);
//		switch (type) {
//			case LCVCUDetectionReq.ID :
//				return new LCVCUDetectionReq();
//			case DcExecuteActionAck.ID :
//				return new DcExecuteActionAck();
//			case DcDisplayTextAck.ID :
//				return new DcDisplayTextAck();
//			case DcExecuteActionReq.ID :
//				return new DcExecuteActionReq();
//			case BOAUpdateLEDConfigReq.ID :
//				return new BOAUpdateLEDConfigReq();
//			case BOAOmloopReq.ID :
//				return new BOAOmloopReq();
//			case KVXmlDeviationMessageReq.ID :
//				return new KVXmlDeviationMessageReq();
//			case KVXmlTimeTableMessageReq.ID :
//				System.out.println("KVXmlTimeTableMessageReq called : " + type );
//				return new KVXmlTimeTableMessageReq();
//			case BOAFreeTextChangeReq.ID :
//				return new BOAFreeTextChangeReq();
//			case BOAPlatformConfigChangeReq.ID :
//				return new BOAPlatformConfigChangeReq();
//			case BOARuleChangeReq.ID :
//				return new BOARuleChangeReq();
//			case BOASwitchOnOffDisplayReq.ID :
//				return new BOASwitchOnOffDisplayReq();
//			case BOAPlatformDisplayLinkReq.ID :
//				return new BOAPlatformDisplayLinkReq();
//			case WDStatusReq.ID				:
//				return new WDStatusReq();
//			case WDErrorStatusAck.ID	:
//				return new WDErrorStatusAck();
//			case BOAAbbreviatedDestNameReq.ID :
//				return new BOAAbbreviatedDestNameReq();
//			case BOAAbbreviatedDestNameAck.ID :
//				return new BOAAbbreviatedDestNameAck();
//			case BOASetGetLBSConfigReq.ID :
//				return new BOASetGetLBSConfigReq();
//			case BOAEntryExitGateInfoReq.ID :
//				return new BOAEntryExitGateInfoReq();
//			case IVRSServiceDataReq.ID :
//				return new IVRSServiceDataReq();
//			case IVRSTripStatusReq.ID :
//				return new IVRSTripStatusReq();
//			case OFPOmloopUpdateReq.ID :
//				return new OFPOmloopUpdateReq();
//			
//			//TODO comment after testing
//			case LCVCUDetectionAck.ID	 		:
//				return new LCVCUDetectionAck(); 
//			case KVXmlTimeTableMessageAck.ID	:
//				return new KVXmlTimeTableMessageAck();
//			case KVXmlDeviationMessageAck.ID 	:
//				return new KVXmlDeviationMessageAck(); 
//			case BOAFreeTextChangeAck.ID		 		:
//				return new BOAFreeTextChangeAck();
//			case BOAOmloopAck.ID		 		:
//				return new BOAOmloopAck();
//			case BOAUpdateLEDConfigAck.ID		:
//				return new BOAUpdateLEDConfigAck();
//			case BOAPlatformConfigChangeAck.ID 	:
//				return new BOAPlatformConfigChangeAck();
//			case BOARuleChangeAck.ID			:
//				return new BOARuleChangeAck();
//			case BOASwitchOnOffDisplayAck.ID	:
//				return new BOASwitchOnOffDisplayAck();
//			case BOAPlatformDisplayLinkAck.ID	:
//				return new BOAPlatformDisplayLinkAck();
//			case BOASetGetLBSConfigAck.ID :
//				return new BOASetGetLBSConfigAck();
//			case BOAEntryExitGateInfoAck.ID :
//				return new BOAEntryExitGateInfoAck();
//			case WDStatusAck.ID					:
//				return new WDStatusAck();
//			case WDErrorStatusReq.ID :
//				return new WDErrorStatusReq();
//			case IVRSServiceDataAck.ID:
//				return new IVRSServiceDataAck();
//			case IvrsDeviationReq.ID :
//				return new IvrsDeviationReq();
//			case OFPOmloopUpdateAck.ID :
//				return new OFPOmloopUpdateAck();
//			case VPSGenericMsg_Req.ID:
//				return new VPSGenericMsg_Req();
//			case VPSGenericMsg_Ack.ID:
//				return new VPSGenericMsg_Ack();
//			case VPSAffectedStops_Ex6.ID:
//				return new VPSAffectedStops_Ex6();
//			case Kv5MessageReq.ID :
//				return new Kv5MessageReq();				
//			case Kv5MessageAck.ID :
//				return new Kv5MessageAck();
//			case KV4XmlMessageReq.ID :
//				return new KV4XmlMessageReq();
//			case BOATrolleyBusGWNChangeReq.ID :
//				return new BOATrolleyBusGWNChangeReq();
//			case BOATrolleyBusGWNChangeAck.ID :
//				return new BOATrolleyBusGWNChangeAck();
//			case BOATimingPointChangeReq.ID :
//				return new BOATimingPointChangeReq();
//			case BOATimingPointChangeAck.ID :
//				return new BOATimingPointChangeAck();
//				
//			case BOAUnUsedPlatformScheduleChangeReq.ID:
//				return new BOAUnUsedPlatformScheduleChangeReq();
//			case BOAUnUsedPlatformScheduleChangeAck.ID:
//				return new BOAUnUsedPlatformScheduleChangeAck();
//			case BOAKv8MessageConfigChangeReq.ID:
//				return new BOAKv8MessageConfigChangeReq();
//			case BOAKv8MessageConfigChangeAck.ID:
//				return new BOAKv8MessageConfigChangeAck();
//			case BOARestartErrorResetReq.ID:
//				return new BOARestartErrorResetReq();
//			case BOARestartErrorResetAck.ID:
//				return new BOARestartErrorResetAck();
//			case BOAKv8FreeTextMessageApprovalReq.ID :
//				return new BOAKv8FreeTextMessageApprovalReq();
//			case BOAKv8FreeTextMessageApprovalAck.ID :
//				return new BOAKv8FreeTextMessageApprovalAck();
//			case ISO1745LCVCUDetectionReq.ID :
//				return new ISO1745LCVCUDetectionReq();
//			//UEC related message 
//			case UECTrolleyList_Req.ID :
//				return new UECTrolleyList_Req();	
//			case UECTrolleyListClear_Req.ID:
//				return new UECTrolleyListClear_Req();	
//			case UECTrolleyBusInfo_Req.ID:
//				return new UECTrolleyBusInfo_Req();	
//			case UECPlatformAllocation_Req.ID:
//				return new UECPlatformAllocation_Req();
//			case UECClearPlatformAllocation_Req.ID:
//				return new UECClearPlatformAllocation_Req();	
//			case UECPreferredPlatform_Req.ID:
//				return new UECPreferredPlatform_Req();	
//			case UECDefaultPlatform_Req.ID:
//				return new UECDefaultPlatform_Req();		
//			case UECClearPreferredPlatform_Req.ID:
//				return new UECClearPreferredPlatform_Req();
//			case UECStatus_Req.ID:
//				return new UECStatus_Req();	
//			case UECKeepAlive_Req.ID:
//				return new UECKeepAlive_Req();
//			case UECBusAssignment_Req.ID:
//				return new UECBusAssignment_Req();
//			case UECChangeModus_Req.ID:
//				return new UECChangeModus_Req();
//			case UECTrolleyInaccessiblePlaforms_Req.ID:
//				return new UECTrolleyInaccessiblePlaforms_Req();
//			case BOAClusterDisplayConfigChangeReq.ID :
//				return new BOAClusterDisplayConfigChangeReq();
//			case BOAPlatformClusterConfigChangeReq.ID :
//				return new BOAPlatformClusterConfigChangeReq();				
//			case CPHCallPlanningMessageReq.ID:
//				return new CPHCallPlanningMessageReq();				
//			case CPHCallNotificationReq.ID:
//				return new CPHCallNotificationReq();		
//			default:
//				System.out.println("Some message recieved here : " + type);
//				break;
//		}
//		return null;
//	}
//
//	public void connectionError() {
//		if (stationManager != null) {
//			stationManager.obcsConnectionError();		
//		}
//	}
//}
