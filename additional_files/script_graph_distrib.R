library(ggplot2)
library(gridExtra)
library(grid)
library(gtable)
library(dplyr)

#******************#
#*   HIGH GINI   *#
#****************#   

#**********#
#   P65   #
#********#

x_real <- subset(P65_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[2,2])

x_fictive <- subset(P65_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[2,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P65_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P65", x="k", y="P(k)", color = "Type")+
  geom_smooth(data = subset(x_real, x_real$y >= min(P65_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P65_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE) + 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.54", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.386", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 53717 \nNumber of distinct subjects = 53678 \nNumber of distinct objects =	548",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#***********#
#   P102   #
#*********#

x_real <- subset(P102_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[10,2])

x_fictive <- subset(P102_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[10,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P102_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P102", x="k", y="P(k)", color = "Type")+
  geom_smooth(data = subset(x_real, x_real$y >= min(P102_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)))+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P102_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type))) + 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.997", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.512", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 443649 \nNumber of distinct subjects = 403894 \nNumber of distinct objects =	9834",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#***********#
#   P103   #
#*********#

x_real <- subset(P103_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[6,2])

x_fictive <- subset(P103_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[6,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P103_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P103", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P103_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P103_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.838", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.439", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 181809 \nNumber of distinct subjects = 180578 \nNumber of distinct objects =	1166",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#***********#
#   P110   #
#*********#

x_real <- subset(P110_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[1,2])

x_fictive <- subset(P110_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[1,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P110_100_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P110", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P110_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P110_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.82", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.709", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 11992 \nNumber of distinct subjects = 10883 \nNumber of distinct objects =	5875",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#***********#
#   P140   #
#*********#

x_real <- subset(P140_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[9,2])

x_fictive <- subset(P140_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[9,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P140_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P140", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P140_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P140_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.5", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.433", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 385022 \nNumber of distinct subjects = 378057 \nNumber of distinct objects =	1474",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#***********#
#   P149   #
#*********#

x_real <- subset(P149_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[4,2])

x_fictive <- subset(P149_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[4,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P149_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P149", x="k", y="P(k)", color = "Type")+
  geom_smooth(data = subset(x_real, x_real$y >= min(P149_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P149_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE) + 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.735", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.346", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 116601 \nNumber of distinct subjects = 110167 \nNumber of distinct objects =	888",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#***********#
#   P170   #
#*********#

x_real <- subset(P170_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[11,2])

x_fictive <- subset(P170_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[11,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P170_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P170", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P170_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P170_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 2.172", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 2.017", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 865931 \nNumber of distinct subjects = 846798 \nNumber of distinct objects =	193223",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#***********#
#   P509   #
#*********#

x_real <- subset(P509_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[3,2])

x_fictive <- subset(P509_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[3,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P509_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P509", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P509_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P509_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.680", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.447", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 109466 \nNumber of distinct subjects = 107635 \nNumber of distinct objects = 1870",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#************#
#   P2079   #
#**********#

x_real <- subset(P2079_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[5,2])

x_fictive <- subset(P2079_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[5,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P2079_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P2079", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P2079_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P2079_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.413", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.358", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 118406 \nNumber of distinct subjects = 98179 \nNumber of distinct objects = 968",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#************#
#   P2094   #
#**********#

x_real <- subset(P2094_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[8,2])

x_fictive <- subset(P2094_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[8,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P2094_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P2094", x="k", y="P(k)", color = "Type")+
  geom_smooth(data = subset(x_real, x_real$y >= min(P2094_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P2094_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE) + 
  scale_x_log10()+ 
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.448", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.395", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 339422 \nNumber of distinct subjects = 334977 \nNumber of distinct objects = 913",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#************#
#   P6379   #
#**********#

x_real <- subset(P6379_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[7,2])

x_fictive <- subset(P6379_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[7,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P6379_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P6379", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P6379_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P6379_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 2.164", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.509", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 246687 \nNumber of distinct subjects = 108073 \nNumber of distinct objects = 5297",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)


#*****************#
#*   LOW GINI   *#
#***************#

#************#
#   P161    #
#**********#

x_real <- subset(P161_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[12,2])

x_fictive <- subset(P161_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[12,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P161_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P161", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P161_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P161_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 3.584", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.886", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 1340394 \nNumber of distinct subjects = 210372 \nNumber of distinct objects = 210372",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#*************#
#   P1547    #
#***********#

x_real <- subset(P1547_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[13,2])

x_fictive <- subset(P1547_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[13,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P1547_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P1547", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P1547_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P1547_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.793", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.829", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 17951 \nNumber of distinct subjects = 7024 \nNumber of distinct objects = 4131",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#************#
#   P181    #
#**********#

x_real <- subset(P181_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[14,2])

x_fictive <- subset(P181_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[14,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P181_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P181", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P181_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P181_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.413", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.606", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 15643 \nNumber of distinct subjects = 15479 \nNumber of distinct objects = 15119",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#*************#
#   P3602    #
#***********#

x_real <- subset(P3602_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[15,2])

x_fictive <- subset(P3602_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[15,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P3602_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P3602", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P3602_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P3602_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 4.658", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.484", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 115259 \nNumber of distinct subjects = 67074 \nNumber of distinct objects = 6169",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)

#************#
#   P451    #
#**********#

x_real <- subset(P451_fictive_real, type == "real", select = c(k))
x_real$alpha <- as.numeric(-powerLaw[16,2])

x_fictive <- subset(P451_fictive_real, type == "fictive", select = c(k))
x_fictive$alpha <- as.numeric(-powerLaw[16,3])

x_real$y <- as.numeric(x_real$k^x_real$alpha)
x_fictive$y <- as.numeric(x_fictive$k^x_fictive$alpha)

x_real$type = "real"
x_fictive$type = "fictive"

p = ggplot(P451_fictive_real, aes(x=k, y=pk, color=as.factor(type))) + geom_point()+ 
  labs(title="The power-law connectivity distribution of objects \n P451", x="k", y="P(k)", color = "Type") +
  geom_smooth(data = subset(x_real, x_real$y >= min(P451_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+
  geom_smooth(data = subset(x_fictive, x_fictive$y >= min(P451_fictive_real[,3])), method="lm", mapping= aes(x = k, y = y, color=as.factor(type)), fullrange = FALSE)+ 
  scale_x_log10()+
  scale_y_log10()+
  annotation_custom(grobTree(textGrob("gamma_real = 1.327", x=0.5,  y=0.92, hjust=0,gp=gpar(col="blue", fontsize=13, fontface="italic"))))+
  annotation_custom(grobTree(textGrob("gamma_fictive = 1.450", x=0.5,  y=0.82, hjust=0,gp=gpar(col="red", fontsize=13, fontface="italic"))))

lab = textGrob((paste("Number of facts = 11682 \nNumber of distinct subjects = 9719 \nNumber of distinct objects = 9862",  sep = " ")),
               x = unit(.1, "npc"), just = c("left"), 
               gp = gpar(fontsize = 10))

gp = ggplotGrob(p)
gp = gtable_add_rows(gp, unit(2, "grobheight", lab), -2)
gp = gtable_add_grob(gp, lab, t = -2, l = gp$layout[gp$layout$name == "panel",]$l)

grid.newpage()
grid.draw(gp)
