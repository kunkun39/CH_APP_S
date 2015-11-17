package com.changhong.system.web.tag;

import com.changhong.system.web.facade.dto.BoxRecommendDTO;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-8-7
 * Time: 上午10:41
 */
public class BoxRecommendTag extends TagSupport {

    private int pageNumber;

    private int recommendPosition;

    private String fileRequestHost;

    private boolean subRecommend;

    private List<BoxRecommendDTO> recommends;

    /**
     * main
     * <img id="1_pos_img_1" src="#" title="精品栏 - 推荐位置一"/>
        <a id="1_pos_name_1" class="name">无推荐</a>

      sub
     <p class="show">
        <img id="1_pos_img_5" src="#" title="精品栏 - 推荐位置五">
    </p>
    <a id="1_pos_name_5" class="name">无推荐</a>
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            String imageId = pageNumber + "_pos_img_" + recommendPosition;
            String nameId = pageNumber + "_pos_name_" + recommendPosition;

            BoxRecommendDTO recommend = null;
            if (recommends != null) {
                for (BoxRecommendDTO dto : recommends) {
                    if (dto.getPageNumber() == pageNumber && dto.getRecommendPosition() == recommendPosition) {
                        recommend = dto;
                    }

                }
            }

            StringBuffer buffer = new StringBuffer();
            if (recommend == null || recommend.getMarketAppId() <= 0) {
                if (!subRecommend) {
                    buffer.append("<img id=\"" + imageId + "\" src=\"\" title=\"推荐位置" + recommendPosition + "\"/>");
                    buffer.append("<a id=\"" + nameId + "\" class=\"name\">位置" + recommendPosition + "</a>");
                } else {
                    buffer.append("<p class=\"show\"><img id=\"" + imageId + "\" src=\"\" title=\"推荐位置" + recommendPosition + "\"/></p>");
                    buffer.append("<a id=\"" + nameId + "\" class=\"name\">位置" + recommendPosition + "</a>");
                }
            } else {
                String imageURL = fileRequestHost + recommend.getAppKey() + "/" + recommend.getPosterActualFileName();
                String appName = recommend.getAppFullName();
                if (!subRecommend) {
                    buffer.append("<img id=\"" + imageId + "\" src=\"" + imageURL + "\" title=\"" + appName + "\"/>");
                    buffer.append("<a id=\"" + nameId + "\" class=\"name\">" + appName + "</a>");
                } else {
                    buffer.append("<p class=\"show\"><img id=\"" + imageId + "\" src=\"" + imageURL + "\" title=\"" + appName + "\"/></p>");
                    buffer.append("<a id=\"" + nameId + "\" class=\"name\">" + appName + "</a>");
                }

            }

            writeMessage(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    protected void writeMessage(String info) throws IOException {
        pageContext.getOut().write(info);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRecommendPosition() {
        return recommendPosition;
    }

    public void setRecommendPosition(int recommendPosition) {
        this.recommendPosition = recommendPosition;
    }

    public String getFileRequestHost() {
        return fileRequestHost;
    }

    public void setFileRequestHost(String fileRequestHost) {
        this.fileRequestHost = fileRequestHost;
    }

    public List<BoxRecommendDTO> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<BoxRecommendDTO> recommends) {
        this.recommends = recommends;
    }

    public boolean isSubRecommend() {
        return subRecommend;
    }

    public void setSubRecommend(boolean subRecommend) {
        this.subRecommend = subRecommend;
    }
}
