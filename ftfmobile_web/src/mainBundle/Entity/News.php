<?php

namespace mainBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * News
 *
 * @ORM\Table(name="news", options={"collate"="utf8mb4_unicode_ci", "charset"="utf8mb4"})
 * @ORM\Entity(repositoryClass="mainBundle\Repository\NewsRepository")
 */
class News
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="cat", type="string", length=10)
     */
    private $cat;


    /**
     * @var string
     *
     * @ORM\Column(name="lang", type="string", length=3)
     */
    private $lang;

    /**
     * @var string
     *
     * @ORM\Column(name="title", type="text")
     */
    private $title;

    /**
     * @var string
     *
     * @ORM\Column(name="date", type="string", length=20)
     */
    private $date;

    /**
     * @var string
     *
     * @ORM\Column(name="imgurl", type="text")
     */
    private $imgurl;


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
     *Get cat
     *
     * @return string
     */
    public function getCat()
    {
        return $this->cat;
    }

    /**
     * Set cat
     *
     * @param string $cat
     * @return News
     */
    public function setCat($cat)
    {
        $this->cat = $cat;
        return $this;
    }


    /**
     * Set lang
     *
     * @param string $lang
     * @return News
     */
    public function setLang($lang)
    {
        $this->lang = $lang;

        return $this;
    }

    /**
     * Get lang
     *
     * @return string 
     */
    public function getLang()
    {
        return $this->lang;
    }

    /**
     * Set title
     *
     * @param string $title
     * @return News
     */
    public function setTitle($title)
    {
        $this->title = $title;

        return $this;
    }

    /**
     * Get title
     *
     * @return string 
     */
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * Set date
     *
     * @param string $date
     * @return News
     */
    public function setDate($date)
    {
        $this->date = $date;

        return $this;
    }

    /**
     * Get date
     *
     * @return string 
     */
    public function getDate()
    {
        return $this->date;
    }

    /**
     * Set imgurl
     *
     * @param string $imgurl
     * @return News
     */
    public function setImgurl($imgurl)
    {
        $this->imgurl = $imgurl;

        return $this;
    }

    /**
     * Get imgurl
     *
     * @return string 
     */
    public function getImgurl()
    {
        return $this->imgurl;
    }

}
