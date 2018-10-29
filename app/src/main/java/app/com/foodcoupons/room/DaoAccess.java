//package app.com.foodcoupons.room;
//
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Query;
//import android.arch.persistence.room.Update;
//
//import java.util.List;
//
//import app.com.esenatenigeria.model.ActsModel;
//import app.com.esenatenigeria.model.BillsModel;
//import app.com.esenatenigeria.model.CommitteeModel;
//import app.com.esenatenigeria.model.InformationModel;
//import app.com.esenatenigeria.model.SenatorDetailModel;
//
///**
// * Created by Shubham verma on 25-04-2018.
// */
//
//@Dao
//public interface DaoAccess {
//
//    // ActsModel db
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertActsOnlySingleRecord(ActsModel.DataBean act);
//
//    @Query("SELECT * FROM ActsDataBean")
//    List<ActsModel.DataBean> fetchActsAllData();
//
//    @Query("SELECT * FROM ActsDataBean WHERE doc_id = :doc_id")
//    ActsModel.DataBean getActsSingleRecord(String doc_id);
//
//    @Query("SELECT * FROM ActsDataBean WHERE doc_id = :doc_id")
//    boolean containActsSingleRecord(String doc_id);
//
//    @Query("SELECT act_codeUpdatedAt FROM ActsDataBean WHERE doc_id = :doc_id")
//    String getUpdatedActsDate(String doc_id);
//
//    @Update
//    void updateActsRecord(ActsModel.DataBean act);
//
//    @Query("DELETE FROM ActsDataBean WHERE doc_id = :doc_id")
//    void deleteActsRecord(String doc_id);
//
//    @Query("DELETE FROM ActsDataBean")
//    void deleteAllActsRecord();
//
//    @Query("UPDATE ActsDataBean SET act_docLocalPath=:path WHERE doc_id=:doc_id ")
//    void updateActsPathRecord(String path, String doc_id);
//
//    ///search acts
//    @Query("SELECT * FROM ActsDataBean  WHERE act_date BETWEEN :dateFrom AND :dateTo")
//    List<ActsModel.DataBean> searchActDate(String dateFrom, String dateTo);
//
//    @Query("SELECT * FROM ActsDataBean  WHERE act_parliament=:parliament")
//    List<ActsModel.DataBean> searchActParliament(String parliament);
//
//    @Query("SELECT * FROM ActsDataBean  WHERE act_session=:session")
//    List<ActsModel.DataBean> searchActSession(String session);
//
//    @Query("SELECT * FROM ActsDataBean  WHERE act_date BETWEEN :dateFrom AND :dateTo AND act_parliament=:parliament")
//    List<ActsModel.DataBean> searchActDateParliament(String dateFrom, String dateTo, String parliament);
//
//    @Query("SELECT * FROM ActsDataBean  WHERE act_date BETWEEN :dateFrom AND :dateTo AND act_session=:session")
//    List<ActsModel.DataBean> searchActDateSession(String dateFrom, String dateTo, String session);
//
//    @Query("SELECT * FROM ActsDataBean  WHERE act_parliament =:parliament AND act_session=:session")
//    List<ActsModel.DataBean> searchActPS(String parliament, String session);
//
//    @Query("SELECT * FROM ActsDataBean  WHERE act_date BETWEEN :dateFrom AND :dateTo AND act_parliament=:parliament AND act_session=:session")
//    List<ActsModel.DataBean> searchActAll(String dateFrom, String dateTo, String parliament, String session);
//    //done
//
//    // BillsModel db
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertBillsOnlySingleRecord(BillsModel.DataBean bill);
//
//    @Query("SELECT * FROM BillsDataBean")
//    List<BillsModel.DataBean> fetchBillsAllData();
//
//    @Query("SELECT * FROM BillsDataBean WHERE doc_id = :doc_id")
//    BillsModel.DataBean getBillsSingleRecord(String doc_id);
//
//    @Query("SELECT * FROM BillsDataBean WHERE doc_id = :doc_id")
//    boolean containBillsSingleRecord(String doc_id);
//
//    @Update
//    void updateBillsRecord(BillsModel.DataBean bill);
//
//    @Query("DELETE FROM BillsDataBean WHERE doc_id = :doc_id")
//    void deleteBillsRecord(String doc_id);
//
//    @Query("DELETE FROM BillsDataBean")
//    void deleteAllBillsRecord();
//
//    @Query("UPDATE BillsDataBean set bill_docLocalPath=:path WHERE doc_id=:doc_id ")
//    void updateBillsPathRecord(String path, String doc_id);
//
//    @Query("SELECT bill_codeUpdatedAt FROM BillsDataBean WHERE doc_id = :doc_id")
//    String getUpdatedBillsDate(String doc_id);
//
//    ///search bills
//    @Query("SELECT * FROM BillsDataBean  WHERE bill_date BETWEEN :dateFrom AND :dateTo")
//    List<BillsModel.DataBean> searchBillsDate(String dateFrom, String dateTo);
//
//    @Query("SELECT * FROM BillsDataBean  WHERE bill_parliament=:parliament")
//    List<BillsModel.DataBean> searchBillsParliament(String parliament);
//
//    @Query("SELECT * FROM BillsDataBean  WHERE bill_session=:session")
//    List<BillsModel.DataBean> searchBillsSession(String session);
//
//    @Query("SELECT * FROM BillsDataBean  WHERE bill_date BETWEEN :dateFrom AND :dateTo AND bill_parliament=:parliament")
//    List<BillsModel.DataBean> searchBillsDateParliament(String dateFrom, String dateTo, String parliament);
//
//    @Query("SELECT * FROM BillsDataBean  WHERE bill_date BETWEEN :dateFrom AND :dateTo AND bill_session=:session")
//    List<BillsModel.DataBean> searchBillsDateSession(String dateFrom, String dateTo, String session);
//
//    @Query("SELECT * FROM BillsDataBean  WHERE bill_parliament =:parliament AND bill_session=:session")
//    List<BillsModel.DataBean> searchBillsPS(String parliament, String session);
//
//    @Query("SELECT * FROM BillsDataBean  WHERE bill_date BETWEEN :dateFrom AND :dateTo AND bill_parliament=:parliament AND bill_session=:session")
//    List<BillsModel.DataBean> searchBillsAll(String dateFrom, String dateTo, String parliament, String session);
//    ///
//
//    // Committee db
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertCommitteeOnlySingleRecord(CommitteeModel.DataBean committee);
//
//    @Query("SELECT * FROM CommitteeDataBean")
//    List<CommitteeModel.DataBean> fetchCommitteeAllData();
//
//    @Query("SELECT * FROM CommitteeDataBean WHERE committee_id = :committee_id")
//    CommitteeModel.DataBean getCommitteeSingleRecord(String committee_id);
//
//    @Query("SELECT * FROM CommitteeDataBean WHERE committee_id = :committee_id")
//    boolean containCommitteeSingleRecord(String committee_id);
//
//    @Update
//    void updateCommitteeRecord(CommitteeModel.DataBean committee);
//
//    @Query("DELETE FROM CommitteeDataBean WHERE committee_id = :committee_id")
//    void deleteCommitteeRecord(String committee_id);
//
//    @Query("DELETE FROM CommitteeDataBean")
//    void deleteAllCommitteeRecord();
//    ///
//
//    // Senator db
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertSenatorsOnlySingleRecord(SenatorDetailModel.DataBean senator);
//
//    @Query("SELECT * FROM SenatorsDetail")
//    List<SenatorDetailModel.DataBean> fetchSenatorsAllData();
//
//    @Query("SELECT * FROM SenatorsDetail WHERE user_id = :user_id")
//    SenatorDetailModel.DataBean getSenatorsSingleRecord(int user_id);
//
//    @Query("SELECT * FROM SenatorsDetail WHERE user_id = :user_id")
//    boolean containSenatorsSingleRecord(int user_id);
//
//    @Update
//    void updateSenatorsRecord(SenatorDetailModel.DataBean senator);
//
//    @Delete
//    void deleteSenatorsRecord(SenatorDetailModel.DataBean senator);
//    ///
//
//    //info
//    @Query("SELECT * FROM InfoDataBean WHERE id = :id")
//    InformationModel.DataBean getInfoSingleRecord(int id);
//
//    @Query("SELECT * FROM InfoDataBean WHERE id = :id")
//    boolean containInfoSingleRecord(int id);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertInfoSingleRecord(InformationModel.DataBean senator);
//
//    @Update
//    void updateInfoRecord(InformationModel.DataBean senator);
//    //
//}
//
