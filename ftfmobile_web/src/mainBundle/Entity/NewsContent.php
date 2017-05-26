<?php

namespace mainBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * NewsContent
 *
 * @ORM\Table(name="news_content", options={"collate"="utf8mb4_unicode_ci", "charset"="utf8mb4"})
 * @ORM\Entity(repositoryClass="mainBundle\Repository\NewsContentRepository")
 */
class NewsContent
{
    /**
     * @ORM\Id
     * @ORM\OneToOne(targetEntity="News")
     * @ORM\JoinColumn(name="id", onDelete="CASCADE")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="websitenewurl", type="text")
     */
    private $websitenewurl;

    /**
     * @var string
     *
     * @ORM\Column(name="mobilenewurl", type="text")
     */
    private $mobilenewurl;



    /**
     * Set id
     *
     * @param integer $id
     * @return NewsContent
     */
    public function setId($id)
    {
        $this->id = $id;

        return $this;
    }


    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set websitenewurl
     *
     * @param string $websitenewurl
     * @return NewsContent
     */
    public function setWebSiteNewUrl($websitenewurl)
    {
        $this->websitenewurl = $websitenewurl;

        return $this;
    }

    /**
     * Get websitenewurl
     *
     * @return string
     */
    public function getWebSiteNewUrl()
    {
        return $this->websitenewurl;
    }

    /**
     * Set mobilesitenewurl
     *
     * @param string $mobilesitenewurl
     * @return NewsContent
     */
    public function setMobileNewUrl($mobilenewurl)
    {
        $this->mobilenewurl = $mobilenewurl;

        return $this;
    }

    /**
     * Get mobilenewurl
     *
     * @return string
     */
    public function getMobileNewUrl()
    {
        return $this->mobilenewurl;
    }
}
