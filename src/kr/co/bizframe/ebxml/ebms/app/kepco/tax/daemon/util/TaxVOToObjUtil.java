/*
 * Created on 2005. 8. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util;

import java.math.BigDecimal;
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.*;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.types.*;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMainVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMetaVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxVOToObjUtil {
    TaxInvoice tax = null;

    public TaxVOToObjUtil(TaxInvoiceVO taxVO){
        this.tax = new TaxInvoice();
        tax.setRevision("1.1");
        setApplicationArea(taxVO);
        setHeader(taxVO.getMain().getUuid(), taxVO.getMain(), taxVO.getMeta());
        setLineList(taxVO.getLine());
        setSummary(taxVO.getMain(), taxVO.getLine());

        System.out.println("uuid in getMeta:"+taxVO.getMeta().getUuid());
//        System.out.println("uuid in getMain:"+taxVO.getMain().getUuid());
    }

    public TaxInvoice getObj(){
        return tax;
    }

    private void setSummary(TaxMainVO vo, ArrayList list) {
        Line[] lineList = new Line[list.size()];
        int totQtyInt = 0;
        for (int i = 0; i < list.size(); i++) {
            Line line = new Line();
            TaxLineVO lineVO = (TaxLineVO) list.get(i);
            totQtyInt = totQtyInt + Integer.parseInt(CommonUtil.nullToZero(lineVO.getQuantity()));
            System.out.println("totQtyInt = "+totQtyInt);
        }

        Summary info = new Summary();
        this.tax.getDataArea().setSummary(info);
        Charge_Amount chargeAmt = new Charge_Amount();
        chargeAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getCharge_amt())));
        info.setCharge_Amount(chargeAmt);

        TotalTax_Amount totTaxAmt = new TotalTax_Amount();
        totTaxAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getTot_tax_amt())));
        info.setTotalTax_Amount(totTaxAmt);

        Total_Quantity totQty = new Total_Quantity();
        totQty.setContent(new BigDecimal(totQtyInt));
        info.setTotal_Quantity(totQty);

        Total_Amount totAmt = new Total_Amount();
        totAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getTot_amt())));
        info.setTotal_Amount(totAmt);

        if(!CommonUtil.nullToZero(vo.getPayment_cash_dc_amt()).equals("0") ||!CommonUtil.nullToZero(vo.getPayment_cash_fc_amt()).equals("0")){
            PaymentMethod cash = new PaymentMethod();
            cash.setPaymentMethod_Code(PaymentMethod_Type.valueOf("10"));
            cash.setDomesticCurrency_Amount(new DomesticCurrency_Amount());
            cash.getDomesticCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_cash_dc_amt())));
            cash.setForeignCurrency_Amount(new ForeignCurrency_Amount());
            cash.getForeignCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_cash_fc_amt())));
            info.addPaymentMethod(cash);
        }

        if(!CommonUtil.nullToZero(vo.getPayment_check_dc_amt()).equals("0") ||!CommonUtil.nullToZero(vo.getPayment_check_fc_amt()).equals("0")){
            PaymentMethod check = new PaymentMethod();
            check.setPaymentMethod_Code(PaymentMethod_Type.valueOf("20"));
            check.setDomesticCurrency_Amount(new DomesticCurrency_Amount());
            check.getDomesticCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_check_dc_amt())));
            check.setForeignCurrency_Amount(new ForeignCurrency_Amount());
            check.getForeignCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_check_fc_amt())));
            info.addPaymentMethod(check);
        }

        if(!CommonUtil.nullToZero(vo.getPayment_bill_dc_amt()).equals("0") ||!CommonUtil.nullToZero(vo.getPayment_bill_fc_amt()).equals("0")){
            PaymentMethod bill = new PaymentMethod();
            bill.setPaymentMethod_Code(PaymentMethod_Type.valueOf("2AA"));
            bill.setDomesticCurrency_Amount(new DomesticCurrency_Amount());
            bill.getDomesticCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_bill_dc_amt())));
            bill.setForeignCurrency_Amount(new ForeignCurrency_Amount());
            bill.getForeignCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_bill_fc_amt())));
            info.addPaymentMethod(bill);
        }

        if(!CommonUtil.nullToZero(vo.getPayment_credit_dc_amt()).equals("0") ||!CommonUtil.nullToZero(vo.getPayment_credit_fc_amt()).equals("0")){
            PaymentMethod credit = new PaymentMethod();
            credit.setPaymentMethod_Code(PaymentMethod_Type.valueOf("30"));
            credit.setDomesticCurrency_Amount(new DomesticCurrency_Amount());
            credit.getDomesticCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_credit_dc_amt())));
            credit.setForeignCurrency_Amount(new ForeignCurrency_Amount());
            credit.getForeignCurrency_Amount().setContent(new BigDecimal(CommonUtil.nullToZero(vo.getPayment_credit_fc_amt())));
            info.addPaymentMethod(credit);
        }
    }


    private void setLineList(ArrayList list) {
        Line[] lineList = new Line[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Line line = new Line();
            TaxLineVO vo = (TaxLineVO) list.get(i);
            lineList[i] = setLine(vo);
        }
        this.tax.getDataArea().setLine(lineList);
    }

    private Line setLine(TaxLineVO vo) {
        Line line = new Line();
        Line_Number num = new Line_Number();
        num.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getLine_num())));
        line.setLine_Number(num);

        line.setTaxItem(new TaxItem());
        line.getTaxItem().setItemIds(new ItemIds());
        line.getTaxItem().getItemIds().setItemId(new ItemId());
        line.getTaxItem().setClassIds(new ClassIds());
        line.getTaxItem().getClassIds().setClassId(new ClassId());
        line.getTaxItem().getClassIds().getClassId().setClass_Identifier(new Class_Identifier());

        Item_Identifier indentifier = new Item_Identifier();
        line.getTaxItem().getItemIds().getItemId().setItem_Identifier(indentifier);

        Item_Name item_name = new Item_Name();
        item_name.setContent(vo.getName());
        line.getTaxItem().addItem_Name(item_name);

        Definition_Text defText = new Definition_Text();
        defText.setContent(vo.getDefine_txt());
        line.getTaxItem().addDefinition_Text(defText);

        Transaction_DateTime transDateTime = new Transaction_DateTime();
        transDateTime.setContent(vo.getTrans_date());
        line.setTransaction_DateTime(transDateTime);

        Item_Quantity itemQuantity = new Item_Quantity();
        itemQuantity.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getQuantity())));
        line.setItem_Quantity(itemQuantity);

        SubTotalItem_Quantity subTotQty = new SubTotalItem_Quantity();
        subTotQty.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getSub_tot_quantity())));
        line.setSubTotalItem_Quantity(subTotQty);

        Item_Basis_Amount itemBasAmt = new Item_Basis_Amount();
        itemBasAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getBasis_amt())));
        line.setItem_Basis_Amount(itemBasAmt);

        Item_Amount itemAmt = new Item_Amount();
        itemAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getAmt())));
        line.setItem_Amount(itemAmt);

        SubTotalItem_Amount subTotAmt = new SubTotalItem_Amount();
        subTotAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getSub_tot_amt())));
        line.setSubTotalItem_Amount(subTotAmt);

        Tax_Amount totAmt = new Tax_Amount();
        totAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getTax_amt())));
        line.setTax_Amount(totAmt);

        SubTotalTax_Amount subTotTaxAmt = new SubTotalTax_Amount();
        subTotTaxAmt.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getTax_sub_tot_amt())));
        line.setSubTotalTax_Amount(subTotTaxAmt);

        ForeignCharge_Amount foreCharge = new ForeignCharge_Amount();
        foreCharge.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getForn_charge_amt())));
        line.setForeignCharge_Amount(foreCharge);

        SubTotalForeignCharge_Amount subTotForeCharge = new SubTotalForeignCharge_Amount();
        subTotForeCharge.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getForn_charge_sub_tot_amt())));
        line.setSubTotalForeignCharge_Amount(subTotForeCharge);

        ExchangeCurrency_Rate_Numeric exchageCurrency = new ExchangeCurrency_Rate_Numeric();
        exchageCurrency.setContent(new BigDecimal(CommonUtil.nullToZero(vo.getExchange_currency_rate())));
        line.setExchangeCurrency_Rate_Numeric(exchageCurrency);

        Description_Text descText = new Description_Text();
        descText.setContent(vo.getLine_desc());
        line.addDescription_Text(descText);
        return line;
    }
    /**
     * setHeader
     * TaxVOToObjUtil
     * void
     * @param header
     */
    private void setHeader(String docId, TaxMainVO mainVO, TaxMetaVO metaVO) {

        this.tax.setDataArea(new DataArea());
        Header info = new Header();
        this.tax.getDataArea().setHeader(info);
        Document_Identifier doc_Identifier = new Document_Identifier();
        doc_Identifier.setContent("");
        info.setDocumentIds(new DocumentIds());
        info.getDocumentIds().setDocumentId(new DocumentId());
        info.getDocumentIds().getDocumentId().setDocument_Identifier(doc_Identifier);

        Document_DateTime docDate = new Document_DateTime();
        docDate.setContent(mainVO.getDoc_date());
        info.setDocument_DateTime(docDate);

        Description_Text text = new Description_Text();
        text.setContent(mainVO.getDoc_desc());
        info.addDescription_Text(text);

        info.setDocumentReferences(new DocumentReferences());

//        InvoiceDocumentReference invoice = new InvoiceDocumentReference();
//        invoice.setDocument_DateTime(new Document_DateTime());
//        invoice.setDocument_Identifier(new Document_Identifier());
//        info.getDocumentReferences().setInvoiceDocumentReference(invoice);

//        OtherDocumentReference other = new OtherDocumentReference();
//        other.setDocument_DateTime(new Document_DateTime());
//        other.setDocument_Identifier(new Document_Identifier());
//        info.getDocumentReferences().setOtherDocumentReference(other);

//        PurchaseOrderReference purchase = new PurchaseOrderReference();
//        purchase.setDocument_DateTime(new Document_DateTime());
//        purchase.setDocument_Identifier(new Document_Identifier());
//        info.getDocumentReferences().setPurchaseOrderReference(purchase);
// 2006.04.28 이제중
        InvoiceDocumentReference invoice = new InvoiceDocumentReference();
        Document_DateTime ref_inv_doc_date = new Document_DateTime();
        ref_inv_doc_date.setContent(mainVO.getRef_inv_doc_date());
        invoice.setDocument_DateTime(ref_inv_doc_date);

        Document_Identifier ref_inv_doc_id = new Document_Identifier();
        //ref_inv_doc_id.setContent(mainVO.getRef_inv_doc_id());		// 수정 200912 김현승
        ref_inv_doc_id.setContent(mainVO.getNts_issue_id());			//국세청연계승인번호
        invoice.setDocument_Identifier(ref_inv_doc_id);
        info.getDocumentReferences().setInvoiceDocumentReference(invoice);

        OtherDocumentReference other = new OtherDocumentReference();
        other.setDocument_DateTime(new Document_DateTime());
        Document_Identifier ref_oth_doc_id = new Document_Identifier();
        //ref_oth_doc_id.setContent(mainVO.getRef_other_doc_id());	// 수정 200912 김현승
        ref_oth_doc_id.setContent(mainVO.getModify_code()); 		// 수정사유코드
        other.setDocument_Identifier(ref_oth_doc_id);
        info.getDocumentReferences().setOtherDocumentReference(other);

        PurchaseOrderReference purchase = new PurchaseOrderReference();
        Document_DateTime pur_inv_doc_date = new Document_DateTime();
        pur_inv_doc_date.setContent(mainVO.getRef_pur_ord_date());
        purchase.setDocument_DateTime(pur_inv_doc_date);

        Document_Identifier pur_inv_doc_id = new Document_Identifier();
        //pur_inv_doc_id.setContent(mainVO.getRef_pur_ord_id());	// 수정 200912 김현승 // 참조주문일자
        pur_inv_doc_id.setContent(mainVO.getDoc_type_detail());		// 세금계산서 발행종류 v3
        purchase.setDocument_Identifier(pur_inv_doc_id);
        info.getDocumentReferences().setPurchaseOrderReference(purchase);

        ImportDeclaration_Identifier import_req_id = new ImportDeclaration_Identifier();
        import_req_id.setContent(mainVO.getImport_reg_id());
        info.setImportDeclaration_Identifier(import_req_id);

// 2006.04.28 이제중

        Volume_Identifier volume = new Volume_Identifier();
        volume.setContent(mainVO.getVolum_id());
        info.setVolume_Identifier(volume);

        Issue_Identifier issue = new Issue_Identifier();
        issue.setContent(mainVO.getIssue_id());
        info.setIssue_Identifier(issue);

        Sequence_Identifier seqId = new Sequence_Identifier();
        seqId.setContent(mainVO.getSeq_id());
        info.setSequence_Identifier(seqId);

        System.out.println("vo.getManagement_id():"+docId);
        info.setManagement_Identifier(docId);

//        info.setImportDeclaration_Identifier(new ImportDeclaration_Identifier());
        info.setBatchIssueStart_DateTime(new BatchIssueStart_DateTime());
        info.setBatchIssueEnd_DateTime(new BatchIssueEnd_DateTime());
        info.setBlank_Number(mainVO.getBlank_num());

        info.setTaxInvoice_Type_Code(TaxInvoice_Type.valueOf(mainVO.getDoc_type_code()));
        info.setBusiness_Type_Code(Business_Type.valueOf(mainVO.getBusiness_type_code()));

        info.setTax_Demand_Indicator(Tax_Demand_Type.valueOf(mainVO.getDemand_type()));

        TotalImport_Quantity quantity = new TotalImport_Quantity();
        quantity.setContent(new BigDecimal(CommonUtil.nullToZero(mainVO.getTot_improt_cnt())));
        info.setTotalImport_Quantity(quantity);

//      ERP 연계 추가 항목
        System.out.println("############################33");
        System.out.println("metaVO.getConstruct_no()::::::::::"+metaVO.getConstruct_no());
        info.setExtension(new Extension());
        Doc_Info docInfo = new Doc_Info();
        docInfo.setExt_Inspector_Sabun(metaVO.getInspector_id());
        docInfo.setExt_Contractor_Sabun(metaVO.getContractor_id());
        docInfo.setExt_Contract_No(metaVO.getContract_no());
        docInfo.setExt_Construct_No(metaVO.getConstruct_no());
        docInfo.setExt_Kisung_Degree_No(metaVO.getKisung_chg_no());
        docInfo.setExt_Report_Type_Code(metaVO.getReport_type());
        docInfo.setExt_Doc_Process_Status(metaVO.getExt_process_status_code());
        docInfo.setExt_Doc_result_Msg(CommonUtil.justNullToBlank(metaVO.getExt_doc_result_msg()));
        docInfo.setExt_Doc_reissue_Msg(CommonUtil.justNullToBlank(metaVO.getExt_doc_reissue_msg()));

        info.getExtension().setDoc_Info(docInfo);

        info.setParties(new Parties());
        setParty(info.getParties(), mainVO);
    }

    private void setParty(Parties info, TaxMainVO vo) {
        info.setBuyerParty(new BuyerParty());
        info.setSupplierParty(new SupplierParty());
        info.setBrokerParty(new BrokerParty());
        setBuyerParty(info.getBuyerParty(), vo);
        setSupplierParty(info.getSupplierParty(), vo);
        setBrokerParty(info.getBrokerParty(), vo);
    }

    private void setBuyerParty(BuyerParty info, TaxMainVO vo) {

        Party_Identifier partyId = new Party_Identifier();
        partyId.setContent(vo.getBuyer_biz_id());
        info.setParty_Identifier(partyId);

        Party_Name partyName = new Party_Name();
        partyName.setContent(vo.getBuyer_president_name());
        info.addParty_Name(partyName);

        info.setBusiness(new Business());

        Organization_Identifier orgIdentifier = new Organization_Identifier();
        //orgIdentifier.setContent(vo.getBuyer_biz_id());	// 수정 200912 김현승
        orgIdentifier.setContent(vo.getBuyer_biz_cd()); 	// 공급받는자종사업장번호
        info.getBusiness().addOrganization_Identifier(orgIdentifier);

        Organization_Name orgName = new Organization_Name();
        System.out.println("vo.getBuyer_name():"+vo.getBuyer_name());
        orgName.setContent(vo.getBuyer_name());
        info.getBusiness().setOrganization_Name(orgName);

        BusinessType_Name busTypeName = new BusinessType_Name();
        busTypeName.setContent(vo.getBuyer_biz_type());
        info.getBusiness().setBusinessType_Name(busTypeName);

        BusinessClassification_Name busClassName = new BusinessClassification_Name();
        busClassName.setContent(vo.getBuyer_biz_class());
        info.getBusiness().setBusinessClassification_Name(busClassName);

        Address_Line1_Text addr_text = new Address_Line1_Text();
        addr_text.setContent(vo.getBuyer_addr());
        Address addr = new Address();
        addr.setAddress_Line1_Text(addr_text);
        info.addAddress(addr);

        Contact contact = new Contact();
        Person person = new Person();
        Person_Name person_name = new Person_Name();
        person.setPerson_Name(person_name);
        person.getPerson_Name().setContent(vo.getBuyer_contactor_name());


        Department dept = new Department();
        Department_Name dept_name = new Department_Name();
        dept_name.setContent(vo.getBuyer_contactor_dept());
        dept.addDepartment_Name(dept_name);

        Telephone_Text tel = new Telephone_Text();
        tel.setContent(vo.getBuyer_contactor_tel());
        Email_Text email = new Email_Text();
        email.setContent(vo.getBuyer_contactor_email());

        contact.addPerson(person);
        contact.addDepartment(dept);
        contact.addTelephone_Text(tel);
        contact.addEmail_Text(email);
        Contacts contacts = new Contacts();
        contacts.setContact(contact);
        info.setContacts(contacts);
    }

    private void setSupplierParty(SupplierParty info, TaxMainVO vo) {

        Party_Identifier partyId = new Party_Identifier();
        partyId.setContent(vo.getSupplier_biz_id());
        info.setParty_Identifier(partyId);

        Party_Name partyName = new Party_Name();
        partyName.setContent(vo.getSupplier_president_name());
        info.addParty_Name(partyName);

        info.setBusiness(new Business());

        Organization_Identifier orgIdentifier = new Organization_Identifier();
        //orgIdentifier.setContent(vo.getSupplier_biz_id()); // 수정 200912 김현승
        orgIdentifier.setContent(vo.getSupplier_biz_cd()); // 공급자종사업장번호
        info.getBusiness().addOrganization_Identifier(orgIdentifier);

        Organization_Name orgName = new Organization_Name();
        orgName.setContent(vo.getSupplier_name());
        info.getBusiness().setOrganization_Name(orgName);

        BusinessType_Name busTypeName = new BusinessType_Name();
        busTypeName.setContent(vo.getSupplier_biz_type());
        info.getBusiness().setBusinessType_Name(busTypeName);

        BusinessClassification_Name busClassName = new BusinessClassification_Name();
        busClassName.setContent(vo.getSupplier_biz_class());
        info.getBusiness().setBusinessClassification_Name(busClassName);

        Address_Line1_Text addr_text = new Address_Line1_Text();
        addr_text.setContent(vo.getSupplier_addr());
        Address addr = new Address();
        addr.setAddress_Line1_Text(addr_text);
        info.addAddress(addr);

        Contact contact = new Contact();
        Person person = new Person();
        Person_Name person_name = new Person_Name();
        person.setPerson_Name(person_name);
        person.getPerson_Name().setContent(vo.getSupplier_contactor_name());


        Department dept = new Department();
        Department_Name dept_name = new Department_Name();
        dept_name.setContent(vo.getSupplier_contactor_dept());
        dept.addDepartment_Name(dept_name);

        Telephone_Text tel = new Telephone_Text();
        tel.setContent(vo.getSupplier_contactor_tel());
        Email_Text email = new Email_Text();
        email.setContent(vo.getSupplier_contactor_email());

        contact.addPerson(person);
        contact.addDepartment(dept);
        contact.addTelephone_Text(tel);
        contact.addEmail_Text(email);
        Contacts contacts = new Contacts();
        contacts.setContact(contact);
        info.setContacts(contacts);
    }

    private void setBrokerParty(BrokerParty info, TaxMainVO vo) {
        Party_Identifier partyId = new Party_Identifier();
        info.setParty_Identifier(partyId);

        Party_Name partyName = new Party_Name();
        info.addParty_Name(partyName);

        info.setBusiness(new Business());

        Organization_Identifier orgIdentifier = new Organization_Identifier();
        info.getBusiness().addOrganization_Identifier(orgIdentifier);

        Organization_Name orgName = new Organization_Name();
        info.getBusiness().setOrganization_Name(orgName);

        BusinessType_Name busTypeName = new BusinessType_Name();
        info.getBusiness().setBusinessType_Name(busTypeName);

        BusinessClassification_Name busClassName = new BusinessClassification_Name();
        info.getBusiness().setBusinessClassification_Name(busClassName);


        Contact contact = new Contact();
        Person person = new Person();
        Person_Name person_name = new Person_Name();
        person.setPerson_Name(person_name);
        person.getPerson_Name().setContent("");


        Department dept = new Department();
        Department_Name dept_name = new Department_Name();
        dept_name.setContent(vo.getSupplier_contactor_dept());
        dept.addDepartment_Name(dept_name);

        Address_Line1_Text addr_text = new Address_Line1_Text();
        Address addr = new Address();
        addr.setAddress_Line1_Text(addr_text);
        info.addAddress(addr);
        Telephone_Text tel = new Telephone_Text();
        Email_Text email = new Email_Text();

        contact.addPerson(person);
        contact.addDepartment(dept);
        contact.addTelephone_Text(tel);
        contact.addEmail_Text(email);
        Contacts contacts = new Contacts();
        contacts.setContact(contact);
        info.setContacts(contacts);
    }

    /**
     * setApplicationArea
     * TaxVOToObjUtil
     * void
     * @param taxVO
     */
    private void setApplicationArea(TaxInvoiceVO taxVO) {
        TaxMetaVO vo = taxVO.getMeta();

        ApplicationArea info = new ApplicationArea();
        this.tax.setApplicationArea(info);
        Creation_DateTime createTime = new Creation_DateTime();
        createTime.setContent(vo.getCreate_time());
        info.setCreation_DateTime(createTime);

        ServiceID_Identifier serviceId = new ServiceID_Identifier();
        serviceId.setContent(vo.getService_id());
        info.setServiceID_Identifier(serviceId);

        Signature sign = new Signature();
        SignatureValue signValue = new SignatureValue();
        signValue.setContent(vo.getSignature().getBytes());

        sign.setSignatureValue(signValue);
        info.addSignature(sign);

        info.setExtension(new Extension());
        Doc_Info doc_info = new Doc_Info();
        doc_info.setDoc_ID(vo.getUuid());
        doc_info.setReceiver_ID(vo.getReceiver_id());
        doc_info.setSender_ID(vo.getSender_id());
        doc_info.setReceiver_Comp_ID(vo.getReceiver_comp_id());
        doc_info.setSender_Comp_ID(vo.getSender_comp_id());
        doc_info.setExt_Doc_result_Status(vo.getExt_doc_result_status());

        doc_info.setExt_System_Type(vo.getExt_system_type());
        doc_info.setExt_Voucher_Type(vo.getExt_voucher_type());
        doc_info.setExt_Purchase_Type(vo.getExt_purchase_type());
        doc_info.setExt_Decuction_Detail(CommonUtil.justNullToBlank(vo.getExt_decuction_detail()));
        doc_info.setExt_Decuction_Status(vo.getExt_decuction_status());
        doc_info.setExt_Buyer_Sabun(vo.getExt_buyer_sabun());
        doc_info.setExt_Valid_Sdate(taxVO.getMain().getDoc_date());
        doc_info.setExt_Valid_Edate(vo.getExt_valid_edate());
        System.out.println("vo.getExt_voucher_buseo():::"+vo.getExt_voucher_buseo());
        doc_info.setExt_Voucher_Buseo(vo.getExt_voucher_buseo());
        doc_info.setExt_Voucher_YearMonth(vo.getExt_voucher_yearMonth());
        doc_info.setExt_Voucher_Seq(vo.getExt_voucher_seq());
        doc_info.setExt_Status_Code(vo.getExt_status_code());
        doc_info.setExt_Consign_Construct_No(vo.getExt_consign_construct_no());
        doc_info.setExt_Consign_Construct_Amount(vo.getExt_consign_construct_amount());
        doc_info.setExt_Revenue_Construct_No(vo.getExt_revenue_construct_no());
        doc_info.setExt_Revenue_Construct_Amount(vo.getExt_revenue_construct_amount());

        info.getExtension().setDoc_Info(doc_info);

    }
}
