USE [ProductionSchedulingSystem_DB]
GO
/****** Object:  Table [dbo].[Attendances]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Attendances](
	[atid] [int] IDENTITY(1,1) NOT NULL,
	[waid] [int] NULL,
	[actualquantity] [int] NULL,
	[alpha] [float] NULL,
	[note] [nvarchar](100) NULL,
 CONSTRAINT [PK__Attendan__5B37D0E9FFB365E9] PRIMARY KEY CLUSTERED 
(
	[atid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Departments]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Departments](
	[did] [int] IDENTITY(1,1) NOT NULL,
	[dname] [nvarchar](100) NOT NULL,
	[type] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[did] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employees]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employees](
	[eid] [int] IDENTITY(1,1) NOT NULL,
	[ename] [nvarchar](100) NOT NULL,
	[did] [int] NULL,
	[phonenumber] [nvarchar](15) NULL,
	[address] [nvarchar](255) NULL,
	[salary] [float] NULL,
 CONSTRAINT [PK__Employee__D9509F6DCFEA79ED] PRIMARY KEY CLUSTERED 
(
	[eid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Features]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Features](
	[fid] [int] NOT NULL,
	[fname] [nvarchar](50) NULL,
	[url] [nvarchar](150) NULL,
 CONSTRAINT [PK_Feature] PRIMARY KEY CLUSTERED 
(
	[fid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PlanDetails]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PlanDetails](
	[pdid] [int] IDENTITY(1,1) NOT NULL,
	[phid] [int] NULL,
	[sid] [int] NULL,
	[date] [date] NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK__PlanDeta__B7E745CD265F44B2] PRIMARY KEY CLUSTERED 
(
	[pdid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PlanHeaders]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PlanHeaders](
	[phid] [int] IDENTITY(1,1) NOT NULL,
	[plid] [int] NULL,
	[pid] [int] NULL,
	[quantity] [int] NULL,
	[estimatedeffort] [float] NULL,
 CONSTRAINT [PK__PlanHead__80E19250AEF340F1] PRIMARY KEY CLUSTERED 
(
	[phid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Plans]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Plans](
	[plid] [int] IDENTITY(1,1) NOT NULL,
	[plname] [nvarchar](100) NOT NULL,
	[startdate] [date] NOT NULL,
	[enddate] [date] NOT NULL,
	[did] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[plid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[pid] [int] IDENTITY(1,1) NOT NULL,
	[pname] [nvarchar](100) NOT NULL,
	[description] [nvarchar](255) NULL,
 CONSTRAINT [PK__Products__B40CC6ED36CCA5BF] PRIMARY KEY CLUSTERED 
(
	[pid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RoleFeature]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoleFeature](
	[rid] [int] NOT NULL,
	[fid] [int] NOT NULL,
 CONSTRAINT [PK_RoleFeature] PRIMARY KEY CLUSTERED 
(
	[rid] ASC,
	[fid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[rid] [int] NOT NULL,
	[rname] [nvarchar](50) NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Salaries]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Salaries](
	[sid] [int] IDENTITY(1,1) NOT NULL,
	[slevel] [nvarchar](10) NOT NULL,
	[salary] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[sid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Shifts]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Shifts](
	[sid] [int] IDENTITY(1,1) NOT NULL,
	[sname] [nvarchar](10) NOT NULL,
	[starttime] [time](7) NOT NULL,
	[endtime] [time](7) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[sid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserRoles]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRoles](
	[rid] [int] NOT NULL,
	[username] [varchar](50) NOT NULL,
 CONSTRAINT [PK_UserRoles] PRIMARY KEY CLUSTERED 
(
	[rid] ASC,
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[username] [varchar](50) NOT NULL,
	[password] [varchar](50) NULL,
	[eid] [int] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WorkAssignments]    Script Date: 10/30/2024 6:05:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WorkAssignments](
	[waid] [int] IDENTITY(1,1) NOT NULL,
	[pdid] [int] NULL,
	[eid] [int] NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[waid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Attendances] ON 

INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (1, 1, 5, 1.25, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (2, 26, 1, 0.5, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (3, 10, 6, 2, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (4, 12, 3, 0.5, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (5, 18, 9, 2.25, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (6, 32, 5, 1, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (7, 33, 2, 0.40000000596046448, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (8, 7, 0, 0, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (9, 21, 5, 1, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (10, 30, 5, 1, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (11, 31, 6, 1, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (12, 27, 10, 1.6666666269302368, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (13, 28, 4, 1, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (14, 35, 7, 0.875, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (15, 29, 3, 1, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (16, 36, 0, 0, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (17, 37, 0, 0, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (18, 38, 0, 0, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (19, 40, 3, 0.75, NULL)
INSERT [dbo].[Attendances] ([atid], [waid], [actualquantity], [alpha], [note]) VALUES (20, 39, 0, 0, NULL)
SET IDENTITY_INSERT [dbo].[Attendances] OFF
GO
SET IDENTITY_INSERT [dbo].[Departments] ON 

INSERT [dbo].[Departments] ([did], [dname], [type]) VALUES (1, N'Administration Department', N'Head Office')
INSERT [dbo].[Departments] ([did], [dname], [type]) VALUES (2, N'Human Resources Department', N'Head Office')
INSERT [dbo].[Departments] ([did], [dname], [type]) VALUES (3, N'Planning Department', N'Head Office')
INSERT [dbo].[Departments] ([did], [dname], [type]) VALUES (4, N'Accounting Department', N'Head Office')
INSERT [dbo].[Departments] ([did], [dname], [type]) VALUES (5, N'Production Workshop No. 1', N'Production')
INSERT [dbo].[Departments] ([did], [dname], [type]) VALUES (6, N'Production Workshop No. 2', N'Production')
INSERT [dbo].[Departments] ([did], [dname], [type]) VALUES (7, N'Production Workshop No. 3', N'Production')
SET IDENTITY_INSERT [dbo].[Departments] OFF
GO
SET IDENTITY_INSERT [dbo].[Employees] ON 

INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (1, N'Alice Johnson', 1, N'0123456789', N'123 Main St, City A', 20)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (2, N'Bob Smith', 2, N'0987654321', N'456 Elm St, City A', 22)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (3, N'Charlie Brown', 3, N'0112233445', N'789 Oak St, City A', 31)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (4, N'Diana Prince', 4, N'0223344556', N'101 Pine St, City A', 17)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (5, N'Eve Adams', 5, N'0334455667', N'111 Maple St, City B', 26)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (6, N'Frank Castle', 5, N'0445566778', N'222 Birch St, City B', 55)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (7, N'Grace Lee', 5, N'0556677889', N'333 Cedar St, City B', 14)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (8, N'Helen Hunt', 5, N'0667788990', N'444 Willow St, City B', 46)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (9, N'Henry Ford', 6, N'0778899001', N'555 Walnut St, City C', 34)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (10, N'Isla Fisher', 6, N'0889900112', N'666 Fir St, City C', 23)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (11, N'Jack Sparrow', 6, N'0990011223', N'777 Chestnut St, City C', 21)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (12, N'Kurt Russell', 6, N'1101122334', N'888 Oak St, City C', 33)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (13, N'Luke Skywalker', 7, N'1212233445', N'999 Ash St, City D', 33)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (14, N'Mia Wallace', 7, N'1323344556', N'101 Elm St, City D', 21)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (15, N'Nathan Drake', 7, N'1434455667', N'202 Pine St, City D', 22)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (16, N'Olivia Wilde', 7, N'1545566778', N'303 Cedar St, City D', 23)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (17, N'John Doe', 5, N'0334455668', N'222 Oak St, City B', 24)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (18, N'Alice Smith', 5, N'0334455669', N'333 Pine St, City B', 25)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (19, N'Michael Brown', 5, N'0334455670', N'444 Cedar St, City B', 21)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (20, N'Laura Davis', 5, N'0334455671', N'555 Birch St, City B', 24)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (21, N'Daniel Garcia', 5, N'0334455672', N'666 Elm St, City B', 25)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (22, N'Sophia Wilson', 5, N'0334455673', N'777 Walnut St, City B', 25)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (23, N'James Martinez', 6, N'0344455674', N'888 Chestnut St, City C', 25)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (24, N'Isabella Clark', 6, N'0344455675', N'999 Redwood St, City C', 26)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (25, N'William Lopez', 6, N'0344455676', N'1010 Fir St, City C', 25)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (26, N'Mia Gonzalez', 6, N'0344455677', N'1111 Spruce St, City C', 26)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (27, N'Benjamin Walker', 6, N'0344455678', N'1212 Poplar St, City C', 36)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (28, N'Emily Lee', 6, N'0344455679', N'1313 Cypress St, City C', 30)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (29, N'Olivia Harris', 7, N'0354455680', N'1414 Palm St, City D', 33)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (30, N'Alexander Young', 7, N'0354455681', N'1515 Olive St, City D', 29)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (31, N'Chloe King', 7, N'0354455682', N'1616 Holly St, City D', 19)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (32, N'Liam Scott', 7, N'0354455683', N'1717 Willow St, City D', 21)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (33, N'Ava Green', 7, N'0354455684', N'1818 Ash St, City D', 25)
INSERT [dbo].[Employees] ([eid], [ename], [did], [phonenumber], [address], [salary]) VALUES (34, N'Noah Baker', 7, N'0354455685', N'1919 Dogwood St, City D', 27)
SET IDENTITY_INSERT [dbo].[Employees] OFF
GO
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (1, N'Production Plan Management', N'/plan/list')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (2, N'Shift Production Tracking', N'/shift-tracking')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (3, N'Attendance Management', N'/attendance-management')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (4, N'Production Reports', N'/production-reports')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (5, N'Worker Performance Tracking', N'/employee-performance')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (6, N'View Plan Detail', N'/plan/detail')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (7, N'Plan Production Create', N'/productionPlan/create')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (8, N'List Plan', N'/productionPlan/list')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (9, N'Delete Plan', N'/productionPlan/delete')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (10, N'Update Plan', N'/productionPlan/update')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (11, N'View Detail Plan', N'/productionPlan/detail')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (12, N'Assignment', N'/productionPlan/assignment')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (13, N'Assignment Create', N'/productionPlan/assignment/create')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (14, N'Attendent', N'/productionPlan/attendent')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (15, N'Feature Plan', N'/productionPlan/feature')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (16, N'Work Infor', N'/productionPlan/workinfor')
INSERT [dbo].[Features] ([fid], [fname], [url]) VALUES (17, N'View Salary', N'/productionPlan/viewsalary')
GO
SET IDENTITY_INSERT [dbo].[PlanDetails] ON 

INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (1, 1, 1, CAST(N'2024-10-01' AS Date), 9)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (2, 2, 1, CAST(N'2024-10-02' AS Date), 3)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (3, 3, 1, CAST(N'2024-10-03' AS Date), 2)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (5, 2, 2, CAST(N'2024-10-02' AS Date), 3)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (6, 3, 2, CAST(N'2024-10-03' AS Date), 2)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (8, 2, 3, CAST(N'2024-10-02' AS Date), 3)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (9, 3, 3, CAST(N'2024-10-03' AS Date), 2)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (11, 106, 1, CAST(N'2024-10-25' AS Date), 9)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (12, 106, 2, CAST(N'2024-10-25' AS Date), 9)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (19, 107, 1, CAST(N'2024-10-25' AS Date), 6)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (22, 106, 1, CAST(N'2024-10-30' AS Date), 1)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (23, 106, 2, CAST(N'2024-10-30' AS Date), 2)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (24, 106, 3, CAST(N'2024-10-30' AS Date), 3)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (27, 107, 2, CAST(N'2024-10-25' AS Date), 1)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (29, 2, 2, CAST(N'2024-10-01' AS Date), 1)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (30, 3, 2, CAST(N'2024-10-01' AS Date), 2)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (31, 1, 2, CAST(N'2024-10-01' AS Date), 55)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (33, 74, 1, CAST(N'2024-10-10' AS Date), 2)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (34, 74, 2, CAST(N'2024-10-10' AS Date), 3)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (35, 74, 3, CAST(N'2024-10-10' AS Date), 4)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (36, 1, 3, CAST(N'2024-10-01' AS Date), 7)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (43, 1, 3, CAST(N'2024-10-03' AS Date), 6)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (51, 192, 1, CAST(N'2024-10-30' AS Date), 1)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (55, 2, 1, CAST(N'2024-10-01' AS Date), 80)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (56, 192, 2, CAST(N'2024-11-01' AS Date), 5)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (57, 192, 2, CAST(N'2024-10-30' AS Date), 7)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (58, 192, 3, CAST(N'2024-10-30' AS Date), 78)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (60, 192, 1, CAST(N'2024-11-05' AS Date), 8)
INSERT [dbo].[PlanDetails] ([pdid], [phid], [sid], [date], [quantity]) VALUES (61, 201, 1, CAST(N'2024-10-30' AS Date), 2)
SET IDENTITY_INSERT [dbo].[PlanDetails] OFF
GO
SET IDENTITY_INSERT [dbo].[PlanHeaders] ON 

INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (1, 1, 1, 112, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (2, 1, 2, 800, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (3, 1, 3, 888, 2)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (74, 2, 1, 130, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (75, 2, 2, 140, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (103, 2, 3, 100, 3)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (106, 19, 1, 2207, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (107, 19, 2, 29, 2)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (175, 40, 1, 3, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (178, 40, 2, 2, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (192, 48, 1, 88, 1)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (201, 48, 2, 16, 1.2000000476837158)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (203, 19, 3, 6, 2)
INSERT [dbo].[PlanHeaders] ([phid], [plid], [pid], [quantity], [estimatedeffort]) VALUES (206, 40, 3, 2, 1)
SET IDENTITY_INSERT [dbo].[PlanHeaders] OFF
GO
SET IDENTITY_INSERT [dbo].[Plans] ON 

INSERT [dbo].[Plans] ([plid], [plname], [startdate], [enddate], [did]) VALUES (1, N'Test Plan for 1st Week of October', CAST(N'2024-10-01' AS Date), CAST(N'2024-11-08' AS Date), 5)
INSERT [dbo].[Plans] ([plid], [plname], [startdate], [enddate], [did]) VALUES (2, N'Test Plan for 2nd Week of October', CAST(N'2024-10-10' AS Date), CAST(N'2024-11-08' AS Date), 6)
INSERT [dbo].[Plans] ([plid], [plname], [startdate], [enddate], [did]) VALUES (19, N'X', CAST(N'2024-10-29' AS Date), CAST(N'2024-11-05' AS Date), 5)
INSERT [dbo].[Plans] ([plid], [plname], [startdate], [enddate], [did]) VALUES (39, N'kk', CAST(N'2024-10-25' AS Date), CAST(N'2024-10-26' AS Date), 7)
INSERT [dbo].[Plans] ([plid], [plname], [startdate], [enddate], [did]) VALUES (40, N'ZZ', CAST(N'2024-10-26' AS Date), CAST(N'2024-11-07' AS Date), 7)
INSERT [dbo].[Plans] ([plid], [plname], [startdate], [enddate], [did]) VALUES (46, N'hhj', CAST(N'2024-10-26' AS Date), CAST(N'2024-10-29' AS Date), 5)
INSERT [dbo].[Plans] ([plid], [plname], [startdate], [enddate], [did]) VALUES (48, N'543345XXCCv6ff', CAST(N'2024-10-30' AS Date), CAST(N'2024-11-14' AS Date), 7)
SET IDENTITY_INSERT [dbo].[Plans] OFF
GO
SET IDENTITY_INSERT [dbo].[Products] ON 

INSERT [dbo].[Products] ([pid], [pname], [description]) VALUES (1, N'Product A', N'Description for Product A')
INSERT [dbo].[Products] ([pid], [pname], [description]) VALUES (2, N'Product B', N'Description for Product B')
INSERT [dbo].[Products] ([pid], [pname], [description]) VALUES (3, N'Product C', N'Description for Product C')
SET IDENTITY_INSERT [dbo].[Products] OFF
GO
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 1)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 2)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 3)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 4)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 5)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 6)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 7)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 8)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 9)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 10)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 11)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 12)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 13)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 14)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 15)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 16)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 17)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (2, 2)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (2, 3)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (3, 3)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (3, 4)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (4, 1)
GO
INSERT [dbo].[Roles] ([rid], [rname]) VALUES (1, N'Workshop Manager')
INSERT [dbo].[Roles] ([rid], [rname]) VALUES (2, N'Worker')
INSERT [dbo].[Roles] ([rid], [rname]) VALUES (3, N'Head of HRM')
INSERT [dbo].[Roles] ([rid], [rname]) VALUES (4, N'Planning Department Staff')
INSERT [dbo].[Roles] ([rid], [rname]) VALUES (5, N'Profile Officer')
GO
SET IDENTITY_INSERT [dbo].[Salaries] ON 

INSERT [dbo].[Salaries] ([sid], [slevel], [salary]) VALUES (1, N'Level 1', CAST(5000.00 AS Decimal(10, 2)))
INSERT [dbo].[Salaries] ([sid], [slevel], [salary]) VALUES (2, N'Level 2', CAST(7000.00 AS Decimal(10, 2)))
INSERT [dbo].[Salaries] ([sid], [slevel], [salary]) VALUES (3, N'Level 3', CAST(9000.00 AS Decimal(10, 2)))
SET IDENTITY_INSERT [dbo].[Salaries] OFF
GO
SET IDENTITY_INSERT [dbo].[Shifts] ON 

INSERT [dbo].[Shifts] ([sid], [sname], [starttime], [endtime]) VALUES (1, N'K1', CAST(N'08:00:00' AS Time), CAST(N'16:00:00' AS Time))
INSERT [dbo].[Shifts] ([sid], [sname], [starttime], [endtime]) VALUES (2, N'K2', CAST(N'16:00:00' AS Time), CAST(N'00:00:00' AS Time))
INSERT [dbo].[Shifts] ([sid], [sname], [starttime], [endtime]) VALUES (3, N'K3', CAST(N'00:00:00' AS Time), CAST(N'08:00:00' AS Time))
SET IDENTITY_INSERT [dbo].[Shifts] OFF
GO
INSERT [dbo].[UserRoles] ([rid], [username]) VALUES (1, N'nguyenvana')
INSERT [dbo].[UserRoles] ([rid], [username]) VALUES (2, N'tranthib')
INSERT [dbo].[UserRoles] ([rid], [username]) VALUES (3, N'levanc')
INSERT [dbo].[UserRoles] ([rid], [username]) VALUES (4, N'phamthid')
INSERT [dbo].[UserRoles] ([rid], [username]) VALUES (5, N'vuvane')
GO
INSERT [dbo].[Users] ([username], [password], [eid]) VALUES (N'levanc', N'123', 1)
INSERT [dbo].[Users] ([username], [password], [eid]) VALUES (N'mra', N'123', 2)
INSERT [dbo].[Users] ([username], [password], [eid]) VALUES (N'nguyenvana', N'123', 3)
INSERT [dbo].[Users] ([username], [password], [eid]) VALUES (N'phamthid', N'123', 4)
INSERT [dbo].[Users] ([username], [password], [eid]) VALUES (N'tranthib', N'123', 5)
INSERT [dbo].[Users] ([username], [password], [eid]) VALUES (N'vuvane', N'123', 6)
GO
SET IDENTITY_INSERT [dbo].[WorkAssignments] ON 

INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (1, 1, 5, 4)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (7, 3, 18, 3)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (10, 31, 6, 3)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (12, 36, 7, 6)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (18, 29, 17, 4)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (21, 9, 20, 5)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (26, 31, 5, 2)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (27, 22, 5, 6)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (28, 51, 13, 4)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (29, 60, 13, 3)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (30, 33, 9, 5)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (31, 34, 12, 6)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (32, 8, 22, 5)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (33, 43, 21, 5)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (35, 61, 29, 8)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (36, 29, 7, 7)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (37, 30, 19, 8)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (38, 2, 19, 6)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (39, 43, 18, 5)
INSERT [dbo].[WorkAssignments] ([waid], [pdid], [eid], [quantity]) VALUES (40, 6, 21, 4)
SET IDENTITY_INSERT [dbo].[WorkAssignments] OFF
GO
ALTER TABLE [dbo].[Attendances]  WITH CHECK ADD  CONSTRAINT [FK__Attendance__waid__6754599E] FOREIGN KEY([waid])
REFERENCES [dbo].[WorkAssignments] ([waid])
GO
ALTER TABLE [dbo].[Attendances] CHECK CONSTRAINT [FK__Attendance__waid__6754599E]
GO
ALTER TABLE [dbo].[Employees]  WITH NOCHECK ADD  CONSTRAINT [FK__Employees__Depar__01142BA1] FOREIGN KEY([did])
REFERENCES [dbo].[Departments] ([did])
GO
ALTER TABLE [dbo].[Employees] CHECK CONSTRAINT [FK__Employees__Depar__01142BA1]
GO
ALTER TABLE [dbo].[PlanDetails]  WITH CHECK ADD  CONSTRAINT [FK__PlanDetai__PlanH__0E6E26BF] FOREIGN KEY([phid])
REFERENCES [dbo].[PlanHeaders] ([phid])
GO
ALTER TABLE [dbo].[PlanDetails] CHECK CONSTRAINT [FK__PlanDetai__PlanH__0E6E26BF]
GO
ALTER TABLE [dbo].[PlanDetails]  WITH CHECK ADD  CONSTRAINT [FK_PlanDetails_Shifts] FOREIGN KEY([sid])
REFERENCES [dbo].[Shifts] ([sid])
GO
ALTER TABLE [dbo].[PlanDetails] CHECK CONSTRAINT [FK_PlanDetails_Shifts]
GO
ALTER TABLE [dbo].[PlanHeaders]  WITH CHECK ADD  CONSTRAINT [FK__PlanHeade__Produ__0B91BA14] FOREIGN KEY([pid])
REFERENCES [dbo].[Products] ([pid])
GO
ALTER TABLE [dbo].[PlanHeaders] CHECK CONSTRAINT [FK__PlanHeade__Produ__0B91BA14]
GO
ALTER TABLE [dbo].[PlanHeaders]  WITH CHECK ADD  CONSTRAINT [FK__PlanHeader__plid__5BE2A6F2] FOREIGN KEY([plid])
REFERENCES [dbo].[Plans] ([plid])
GO
ALTER TABLE [dbo].[PlanHeaders] CHECK CONSTRAINT [FK__PlanHeader__plid__5BE2A6F2]
GO
ALTER TABLE [dbo].[Plans]  WITH CHECK ADD FOREIGN KEY([did])
REFERENCES [dbo].[Departments] ([did])
GO
ALTER TABLE [dbo].[WorkAssignments]  WITH CHECK ADD  CONSTRAINT [FK__WorkAssig__PlanD__1332DBDC] FOREIGN KEY([pdid])
REFERENCES [dbo].[PlanDetails] ([pdid])
GO
ALTER TABLE [dbo].[WorkAssignments] CHECK CONSTRAINT [FK__WorkAssig__PlanD__1332DBDC]
GO
