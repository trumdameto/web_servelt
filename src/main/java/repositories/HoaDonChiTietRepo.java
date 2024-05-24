package repositories;

import entities.HDCT;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;

public class HoaDonChiTietRepo {
	private Session hSession;
	public HoaDonChiTietRepo() {
		this.hSession = HibernateUtil.getFACTORY().openSession();
	}

	public List<HDCT> findAll() {
		String hql = "Select entity from HDCT entity";
		Query query = this.hSession.createQuery(hql, HDCT.class);
		return query.getResultList();
	}

	public void create(HDCT ms) {
		Transaction transaction = hSession.getTransaction();
		try {
			transaction.begin();
			this.hSession.persist(ms);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	public void update(HDCT ms) {
		Transaction transaction = hSession.getTransaction();
		try {
			transaction.begin();
			this.hSession.merge(ms);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	public void delete(HDCT ms) {
		Transaction transaction = hSession.getTransaction();
		try {
			transaction.begin();
			this.hSession.remove(ms);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	public HDCT findById(int id) {
		return this.hSession.find(HDCT.class,id);
	}
}
