package org.ehais.shop.service.impl;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.tp.TpDiningTableMapper;
import org.ehais.shop.model.tp.TpDiningTable;
import org.ehais.shop.model.tp.TpDiningTableExample;
import org.ehais.shop.service.TpDiningTableService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.MatrixToImageWriter;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("tpDiningTableService")
public class TpDiningTableServiceImpl  extends CommonServiceImpl implements TpDiningTableService{
	
	@Autowired
	private TpDiningTableMapper tpDiningTableMapper;

	@Autowired
	protected EWPPublicService eWPPublicService;

	
	public ReturnObject<TpDiningTable> diningtable_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<TpDiningTable> diningtable_list_json(HttpServletRequest request,EConditionObject condition,String tablename) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		c.andStoreIdEqualTo(condition.getStore_id());
		if(StringUtils.isNotEmpty(tablename))c.andTablenameLike("%"+tablename+"%");
		List<TpDiningTable> list = tpDiningTableMapper.selectByExample(example);
		long total = tpDiningTableMapper.countByExample(example);

		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<TpDiningTable> diningtable_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		TpDiningTable model = new TpDiningTable();


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<TpDiningTable> diningtable_insert_submit(HttpServletRequest request,TpDiningTable model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		c.andTablenameEqualTo(model.getTablename());
		c.andStoreIdEqualTo(store_id);
		long count = tpDiningTableMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = tpDiningTableMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}
	
	
	public ReturnObject<TpDiningTable> diningtable_insert_batch_submit(HttpServletRequest request,String prifix,Integer startNo,Integer endNo)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		
		for(Integer i = startNo ; i <= endNo ; i++){
			example.clear();
			c = example.createCriteria();
			c.andTablenameEqualTo(prifix+i.toString());
			c.andStoreIdEqualTo(store_id);
			long count = tpDiningTableMapper.countByExample(example);
			if(count == 0){
				TpDiningTable model = new TpDiningTable();
				model.setStoreId(store_id);
				model.setTablename(prifix+i.toString());
				model.setIsValid(Short.valueOf("1"));
				tpDiningTableMapper.insertSelective(model);
			}
		}


		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<TpDiningTable> diningtable_update(HttpServletRequest request,Long dtId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		c.andDtIdEqualTo(dtId);
		c.andStoreIdEqualTo(store_id);
		List<TpDiningTable> list = tpDiningTableMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningTable model = list.get(0);



		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<TpDiningTable> diningtable_update_submit(HttpServletRequest request,TpDiningTable model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		
		c.andDtIdEqualTo(model.getDtId());
		c.andStoreIdEqualTo(store_id);

		long count = tpDiningTableMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		TpDiningTable bean = tpDiningTableMapper.selectByPrimaryKey(model.getDtId());

bean.setTablename(model.getTablename());
bean.setIsValid(model.getIsValid());


		int code = tpDiningTableMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<TpDiningTable> diningtable_info(HttpServletRequest request,Long dtId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		c.andDtIdEqualTo(dtId);
		c.andStoreIdEqualTo(store_id);
		List<TpDiningTable> list = tpDiningTableMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningTable model = list.get(0);


		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<TpDiningTable> diningtable_find(HttpServletRequest request,Long dtId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		c.andDtIdEqualTo(dtId);
		c.andStoreIdEqualTo(store_id);
		List<TpDiningTable> list = tpDiningTableMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningTable model = list.get(0);
		

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<TpDiningTable> diningtable_delete(HttpServletRequest request,Long dtId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningTable> rm = new ReturnObject<TpDiningTable>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andDtIdEqualTo(dtId);

		long count = tpDiningTableMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = tpDiningTableMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,TpDiningTable model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "diningtable.xml",model,"tp_diningtable",optionMap);
		
		
		return bootStrapList;
	}

	@Override
	public void diningtable_export(HttpServletRequest request,HttpServletResponse response, Long dtId,Integer download,Integer preview) throws Exception {
		// TODO Auto-generated method stub
		TpDiningTableExample example = new TpDiningTableExample();
		TpDiningTableExample.Criteria c = example.createCriteria();
		c.andDtIdEqualTo(dtId);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		c.andStoreIdEqualTo(store_id);
		List<TpDiningTable> list = tpDiningTableMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			return ;
		}
		TpDiningTable model = list.get(0);
//		String session_supplierssn = (String)request.getSession().getAttribute("session_supplierssn");
//		String content = "http://w.ehais.com/api.php/Api/DiningApi/wxgo?dining="+session_supplierssn+"&tableno="+model.getTablename();
		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
		String content = request.getScheme()+"://"+request.getServerName()+"/diningStore!"+SignUtil.setDiningId(store_id, 0, 0L, 0L, model.getTablename(), wpPublic.getToken());
		
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		@SuppressWarnings("rawtypes")
        Map hints = new HashMap();
        
        //设置UTF-8， 防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN,1);
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        
        //width:图片完整的宽;height:图片完整的高
        //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
        int width = 400;
        int height = 500;
        
        //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//        
        Graphics2D g = image.createGraphics();
        String pressText = "台号："+model.getTablename();
        int fontSize = 28; //字体大小
        int fontStyle = 1; //字体风格
        
        int startX = (width-(fontSize*pressText.length()))/2;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = 40; 
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, fontStyle, fontSize));
        g.drawString(pressText, startX, startY);
        
        
        fontSize = 16; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "广州易海司信息科技有限公司";
        startX = (width-(fontSize*pressText.length()))/2;
        startY = height-(height-width)/2+10;
        g.drawString(pressText, startX, startY );
        
        fontSize = 12; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "公众号：gzehais     微客服：haisoftware";
        startX = (width-(fontSize*pressText.length())) + (fontSize*pressText.length()) / 6 ;
        g.drawString(pressText, startX, startY + 30);
        
        
        g.dispose();
        
        if(download != null && download == 1){//下载
        	response.setContentType("application/octet-stream");  
            response.setHeader("Accept-Ranges", "bytes");  
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(model.getTablename().getBytes("utf-8"), "ISO8859-1")+".jpg");  
            
        }
         
        
        OutputStream stream = response.getOutputStream();
        ImageIO.write(image, "jpg", stream);
        
        
	}


	
}



