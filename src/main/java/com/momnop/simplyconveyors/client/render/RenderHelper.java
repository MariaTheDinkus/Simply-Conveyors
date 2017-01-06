package com.momnop.simplyconveyors.client.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.PriorityQueue;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHelper
{
	/**
	 * @description Port of some Tessellator methods from 1.7.10. Mostly the
	 *              ones for drawing quads.
	 */

	private static int nativeBufferSize = 0x200000;
	private static int trivertsInBuffer = (nativeBufferSize / 48) * 6;
	public static boolean renderingWorldRenderer = false;
	public boolean defaultTexture = false;
	private int rawBufferSize = 0;
	public int textureID = 0;

	/** The byte buffer used for GL allocation. */
	private static ByteBuffer byteBuffer = GLAllocation.createDirectByteBuffer(nativeBufferSize * 4);
	/** The same memory as byteBuffer, but referenced as an integer buffer. */
	private static IntBuffer intBuffer = byteBuffer.asIntBuffer();
	/** The same memory as byteBuffer, but referenced as an float buffer. */
	private static FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
	/** The same memory as byteBuffer, but referenced as an short buffer. */
	private static ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
	/** Raw integer array. */
	private int[] rawBuffer;
	/**
	 * The number of vertices to be drawn in the next draw call. Reset to 0
	 * between draw calls.
	 */
	private int vertexCount;
	/** The first coordinate to be used for the texture. */
	private double textureU;
	/** The second coordinate to be used for the texture. */
	private double textureV;
	private int brightness;
	/** The color (RGBA) value to be used for the following draw call. */
	private int color;
	/** Whether the current draw object for this tessellator has color values. */
	private boolean hasColor;
	/**
	 * Whether the current draw object for this tessellator has texture
	 * coordinates.
	 */
	private boolean hasTexture;
	private boolean hasBrightness;
	/** Whether the current draw object for this tessellator has normal values. */
	private boolean hasNormals;
	/** The index into the raw buffer to be used for the next data. */
	private int rawBufferIndex;
	/**
	 * The number of vertices manually added to the given draw call. This
	 * differs from vertexCount because it adds extra vertices when converting
	 * quads to triangles.
	 */
	private int addedVertices;
	/** Disables all color information for the following draw call. */
	private boolean isColorDisabled;
	/** The draw mode currently being used by the tessellator. */
	private int drawMode;
	/**
	 * An offset to be applied along the x-axis for all vertices in this draw
	 * call.
	 */
	private double xOffset;
	/**
	 * An offset to be applied along the y-axis for all vertices in this draw
	 * call.
	 */
	private double yOffset;
	/**
	 * An offset to be applied along the z-axis for all vertices in this draw
	 * call.
	 */
	private double zOffset;
	/** The normal to be applied to the face being drawn. */
	private int normal;
	/** The static instance of the Tessellator. */
	private static final RenderHelper instance = new RenderHelper();
	/** Whether this tessellator is currently in draw mode. */
	private boolean isDrawing;
	/** The size of the buffers used (in integers). */
	private int bufferSize;
	private static final String __OBFID = "CL_00000960";

	public static RenderHelper getInstance()
	{
		return instance;
	}

	/**
	 * Draws the data set up in this tessellator and resets the state to prepare
	 * for new drawing.
	 */
	public int draw()
	{
		if(!this.isDrawing)
		{
			throw new IllegalStateException("Not tesselating!");
		}
		else
		{
			this.isDrawing = false;

			int offs = 0;
			while (offs < vertexCount)
			{
				int vtc = Math.min(vertexCount - offs, nativeBufferSize >> 5);
				this.intBuffer.clear();
				this.intBuffer.put(this.rawBuffer, offs * 8, vtc * 8);
				this.byteBuffer.position(0);
				this.byteBuffer.limit(vtc * 32);
				offs += vtc;

				if(this.hasTexture)
				{
					this.floatBuffer.position(3);
					GL11.glTexCoordPointer(2, 32, this.floatBuffer);
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}

				if(this.hasBrightness)
				{
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
					this.shortBuffer.position(14);
					GL11.glTexCoordPointer(2, 32, this.shortBuffer);
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
				}

				if(this.hasColor)
				{
					this.byteBuffer.position(20);
					GL11.glColorPointer(4, true, 32, this.byteBuffer);
					GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
				}

				if(this.hasNormals)
				{
					this.byteBuffer.position(24);
					GL11.glNormalPointer(32, this.byteBuffer);
					GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
				}

				this.floatBuffer.position(0);
				GL11.glVertexPointer(3, 32, this.floatBuffer);
				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				GL11.glDrawArrays(this.drawMode, 0, vtc);
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

				if(this.hasTexture)
				{
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}

				if(this.hasBrightness)
				{
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
				}

				if(this.hasColor)
				{
					GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
				}

				if(this.hasNormals)
				{
					GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				}
			}

			if(rawBufferSize > 0x20000 && rawBufferIndex < (rawBufferSize << 3))
			{
				rawBufferSize = 0x10000;
				rawBuffer = new int[rawBufferSize];
			}

			int i = this.rawBufferIndex * 4;
			this.reset();
			return i;
		}
	}

	/**
	 * Clears the tessellator state in preparation for new drawing.
	 */
	private void reset()
	{
		this.vertexCount = 0;
		this.byteBuffer.clear();
		this.rawBufferIndex = 0;
		this.addedVertices = 0;
	}

	/**
	 * Resets tessellator state and prepares for drawing (with the specified
	 * draw mode).
	 */
	public void startDrawing(int p_78371_1_)
	{
		if(this.isDrawing)
		{
			throw new IllegalStateException("Already tesselating!");
		}
		else
		{
			this.isDrawing = true;
			this.reset();
			this.drawMode = p_78371_1_;
			this.hasNormals = false;
			this.hasColor = false;
			this.hasTexture = false;
			this.hasBrightness = false;
			this.isColorDisabled = false;
		}
	}

	/**
	 * Sets the texture coordinates.
	 */
	public void setTextureUV(double p_78385_1_, double p_78385_3_)
	{
		this.hasTexture = true;
		this.textureU = p_78385_1_;
		this.textureV = p_78385_3_;
	}

	/**
	 * Adds a vertex specifying both x,y,z and the texture u,v for it.
	 */
	public void addVertexWithUV(double p_78374_1_, double p_78374_3_, double p_78374_5_, double p_78374_7_, double p_78374_9_)
	{
		this.setTextureUV(p_78374_7_, p_78374_9_);
		this.addVertex(p_78374_1_, p_78374_3_, p_78374_5_);
	}

	/**
	 * Adds a vertex with the specified x,y,z to the current draw call. It will
	 * trigger a draw() if the buffer gets full.
	 */
	public void addVertex(double p_78377_1_, double p_78377_3_, double p_78377_5_)
	{
		if(rawBufferIndex >= rawBufferSize - 32)
		{
			if(rawBufferSize == 0)
			{
				rawBufferSize = 0x10000;
				rawBuffer = new int[rawBufferSize];
			}
			else
			{
				rawBufferSize *= 2;
				rawBuffer = Arrays.copyOf(rawBuffer, rawBufferSize);
			}
		}
		++this.addedVertices;

		if(this.hasTexture)
		{
			this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float) this.textureU);
			this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float) this.textureV);
		}

		if(this.hasBrightness)
		{
			this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
		}

		if(this.hasColor)
		{
			this.rawBuffer[this.rawBufferIndex + 5] = this.color;
		}

		if(this.hasNormals)
		{
			this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
		}

		this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float) (p_78377_1_ + this.xOffset));
		this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float) (p_78377_3_ + this.yOffset));
		this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float) (p_78377_5_ + this.zOffset));
		this.rawBufferIndex += 8;
		++this.vertexCount;
	}

	/**
	 * Sets the normal for the current draw call.
	 */
	public void setNormal(float p_78375_1_, float p_78375_2_, float p_78375_3_)
	{
		this.hasNormals = true;
		byte b0 = (byte) ((int) (p_78375_1_ * 127.0F));
		byte b1 = (byte) ((int) (p_78375_2_ * 127.0F));
		byte b2 = (byte) ((int) (p_78375_3_ * 127.0F));
		this.normal = b0 & 255 | (b1 & 255) << 8 | (b2 & 255) << 16;
	}

	/**
	 * Sets the RGBA values for the color, converting from floats between 0 and
	 * 1 to integers from 0-255.
	 */
	public void setColorRGBA_F(float p_78369_1_, float p_78369_2_, float p_78369_3_, float p_78369_4_)
	{
		this.setColorRGBA((int) (p_78369_1_ * 255.0F), (int) (p_78369_2_ * 255.0F), (int) (p_78369_3_ * 255.0F), (int) (p_78369_4_ * 255.0F));
	}

	/**
	 * Sets the RGBA values for the color. Also clamps them to 0-255.
	 */
	public void setColorRGBA(int p_78370_1_, int p_78370_2_, int p_78370_3_, int p_78370_4_)
	{
		if(!this.isColorDisabled)
		{
			if(p_78370_1_ > 255)
			{
				p_78370_1_ = 255;
			}

			if(p_78370_2_ > 255)
			{
				p_78370_2_ = 255;
			}

			if(p_78370_3_ > 255)
			{
				p_78370_3_ = 255;
			}

			if(p_78370_4_ > 255)
			{
				p_78370_4_ = 255;
			}

			if(p_78370_1_ < 0)
			{
				p_78370_1_ = 0;
			}

			if(p_78370_2_ < 0)
			{
				p_78370_2_ = 0;
			}

			if(p_78370_3_ < 0)
			{
				p_78370_3_ = 0;
			}

			if(p_78370_4_ < 0)
			{
				p_78370_4_ = 0;
			}

			this.hasColor = true;

			if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
			{
				this.color = p_78370_4_ << 24 | p_78370_3_ << 16 | p_78370_2_ << 8 | p_78370_1_;
			}
			else
			{
				this.color = p_78370_1_ << 24 | p_78370_2_ << 16 | p_78370_3_ << 8 | p_78370_4_;
			}
		}
	}
}